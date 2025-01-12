package com.nabssam.bestbook.data.repository

import android.util.Log
import com.nabssam.bestbook.data.remote.api.AuthApiService
import com.nabssam.bestbook.data.remote.dto.SignInRequest
import com.nabssam.bestbook.data.remote.dto.SignUpRequest
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authApiService: AuthApiService,
    private val userPreferences: UserPreferencesRepository
) {
    suspend fun signIn(email: String, password: String): Result<Unit> {
        return try {
            val request = SignInRequest(email, password)
            val response = authApiService.signIn(request)
            //val data = authApiService.signIn(request).body()?.data

            Log.d("LOGIN_RESPONSE", "${response}")
            Log.d("LOGIN_RESPONSE_BODY", "${response.body() ?: "Empty body"}")

            if (response.isSuccessful) {    // is successful: Returns true if code() is in the range [200..300)
                response.body()?.let { authResponse ->
                    if (authResponse.success) {
                        // Save tokens and user data
                        userPreferences.saveAuthTokens(
                            authResponse.data.accessToken,
                            authResponse.data.refreshToken
                        )
                        userPreferences.saveUser(authResponse.data.user)
                        Result.success(Unit)
                    } else {
                        Result.failure(Exception(authResponse.message))
                    }
                    Result.success(Unit)

                } ?: Result.failure(Exception("Empty response"))
            } else {
                when (response.code()) {
                    400 -> Result.failure(Exception("400:Bad Request: Invalid input data"))
                    401 -> Result.failure(Exception("Unauthorized: Invalid email or password"))
                    403 -> Result.failure(Exception("Forbidden: Access denied"))
                    404 -> Result.failure(Exception("Not Found: Endpoint not found"))
                    500 -> Result.failure(Exception("Server Error: Please try again later"))
                    else -> Result.failure(Exception("Unexpected Error: ${response.code()}"))
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun signUp(request: SignUpRequest): Result<Unit> {
        return try {
            val response = authApiService.register(request)
            if (response.isSuccessful) {
                response.body()?.let { registerResponse ->
                    //userPreferences.saveUser(registerResponse.data.user)
                    Result.success(Unit)
                } ?: Result.failure(Exception("Empty response"))
            } else {
                when (response.code()) {
                    400 -> Result.failure(Exception("400:Bad Request: Invalid input data"))
                    401 -> Result.failure(Exception("Unauthorized: Invalid email or password"))
                    403 -> Result.failure(Exception("Forbidden: Access denied"))
                    404 -> Result.failure(Exception("Not Found: Endpoint not found"))
                    500 -> Result.failure(Exception("Server Error: Please try again later"))
                    else -> Result.failure(Exception("Unexpected Error: ${response.code()}"))
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}