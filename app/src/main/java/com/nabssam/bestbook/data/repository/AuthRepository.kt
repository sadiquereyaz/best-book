package com.nabssam.bestbook.data.repository

import android.util.Log
import com.nabssam.bestbook.data.mapper.UserMapper
import com.nabssam.bestbook.data.remote.api.AuthApiService
import com.nabssam.bestbook.data.remote.dto.auth.SignInRequest
import com.nabssam.bestbook.data.remote.dto.auth.SignUpRequest
import com.nabssam.bestbook.data.remote.dto.auth.VerifyOtpRequest
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authApiService: AuthApiService,
    private val userPreferences: UserPreferencesRepository,
    private val userMapper: UserMapper
) {
    suspend fun signIn(email: String, password: String): Result<Unit> {
        return try {
            val request = SignInRequest(email, password)
            val response = authApiService.signIn(request)
            Log.d("LOGIN_RESPONSE", "${response}")
            Log.d("LOGIN_RESPONSE_BODY", "${response.body() ?: "Empty body"}")

            if (response.isSuccessful) {    // is successful: Returns true if code() is in the range [200..300)
                response.body()?.let { authResponse ->
                    // TODO Save users information into room as well
                    userPreferences.saveUser(userMapper.dtoToDomain(authResponse))
                    Result.success(Unit)

                } ?: Result.failure(Exception("Empty response"))
            } else {
                when (response.code()) {
                    400 -> Result.failure(Exception("400 Bad Request: Invalid input data"))
                    401 -> Result.failure(Exception("Unauthorized: Invalid email or password"))
                    403 -> Result.failure(Exception("Forbidden: Access denied"))
                    404 -> Result.failure(Exception("User doesn't exist"))
                    500 -> Result.failure(Exception("Server Error: Please try again later"))
                    else -> Result.failure(Exception("Unexpected Error: ${response.code()}"))
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun signUp(request: SignUpRequest): Result<String> {
        return try {
            val response = authApiService.register(request)
            if (response.isSuccessful) {
                response.body()?.let { registerResponse ->
                    Result.success(registerResponse.message)
                } ?: Result.failure(Exception("Empty signup response"))
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

    suspend fun verifyOtp(request: VerifyOtpRequest): Result<String> {
        return try {
            val response = authApiService.verifyOtp(request)
            if (response.isSuccessful) {
                response.body()?.let { res ->
                    Result.success(res.message)
                } ?: Result.failure(Exception("Empty signup response"))
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