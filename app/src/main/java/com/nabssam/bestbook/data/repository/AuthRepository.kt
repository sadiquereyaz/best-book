package com.nabssam.bestbook.data.repository

import com.nabssam.bestbook.data.remote.api.AuthApiService
import com.nabssam.bestbook.domain.model.AuthData
import com.nabssam.bestbook.domain.model.RegisterRequest
import com.nabssam.bestbook.domain.model.SignInRequest
import com.nabssam.bestbook.domain.model.register.RegisterResponse
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authApiService: AuthApiService,
    private val userPreferences: UserPreferencesRepository
) {
    suspend fun signIn(email: String, password: String): Result<Unit> {
        return try {
            val request = SignInRequest(email, password)
            val response = authApiService.signIn(request)

            if (response.isSuccessful) {
                response.body()?.let { authResponse ->
                    if (authResponse.success) {
                        // Save tokens and user data
                        userPreferences.saveAuthTokens(
                            authResponse.data.accessToken,
                            authResponse.data.refreshToken
                        )
                        userPreferences.saveUser(authResponse.data.user)
                        Result.success(Unit/*authResponse.data*/)
                    } else {
                        Result.failure(Exception(authResponse.message))
                    }
                } ?: Result.failure(Exception("Empty response"))
            } else {
                Result.failure(Exception(response.errorBody()?.string() ?: "Sign in failed"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }

    }

    suspend fun register(
        name: String,
        email: String,
        password: String,
        phone: String?
    ): Result<Unit> {
        return try {
            val request = RegisterRequest(name, email, password)
            val response = authApiService.register(request)
            if (response.isSuccessful)
            //{
               // response.body()?.let { registerResponse ->
                    //userPreferences.saveUser(registerResponse.data.user)
                    Result.success(
                        Unit
//                    registerResponse
                    )
              //  } ?: Result.failure(Exception("Empty response"))
           // } else {
                Result.failure(Exception(response.errorBody()?.string() ?: "Registration failed"))
           // }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}