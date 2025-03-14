package com.nabssam.bestbook.data.repository.auth

import com.nabssam.bestbook.data.mapper.UserMapper
import com.nabssam.bestbook.data.remote.api.AuthApiService
import com.nabssam.bestbook.data.remote.dto.auth.SignInRequest
import com.nabssam.bestbook.data.remote.dto.auth.SignUpRequest
import com.nabssam.bestbook.data.remote.dto.auth.VerifyOtpRequest
import com.nabssam.bestbook.data.repository.UserPrefRepoImpl
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authApiService: AuthApiService,
    private val userPreferences: UserPrefRepoImpl,
    private val userMapper: UserMapper
) {

    suspend fun signIn(email: String, password: String): Result<Unit> {
        return try {
            //throw Exception("Testing Exception")
            val request = SignInRequest(email, password)
            val response = authApiService.signIn(request)

            if (response.isSuccessful) {    // is successful: Returns true if code() is in the range [200..300)
                response.body()?.let { authResponse ->
                    // TODO Save users information into room as well
                    //Log.d.d("AuthRepository", """User data from server: $authResponse""")
                    userPreferences.saveUser(userMapper.dtoToDomain(authResponse.user))
                    Result.success(Unit)
                } ?: Result.failure(Exception("Empty response"))
            } else {
                when (response.code()) {
                    400 -> Result.failure(Exception("Wrong username or password"))
                    401 -> Result.failure(Exception("Unauthorized: Invalid username or password"))
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

    suspend fun logout(): Result<String?> {
        return try {
            userPreferences.clearAll()
            Result.success(null)
        } catch (e: Exception) {
            Result.failure(e.cause ?: Exception("Unexpected Error"))
        }
    }
}