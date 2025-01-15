package com.nabssam.bestbook.data.repository.auth

import android.util.Log
import com.nabssam.bestbook.domain.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Inject


// 2. Implement token storage using UserPreferences
class UserPreferencesTokenStorage @Inject constructor(
    private val userPreferences: UserPreferencesRepository
) : TokenStorage {
    override suspend fun getAccessToken() = userPreferences.accessToken.firstOrNull()
    override suspend fun getRefreshToken() = userPreferences.refreshToken.firstOrNull()
    override suspend fun saveTokens(accessToken: String, refreshToken: String) {
        userPreferences.saveTokens(accessToken, refreshToken)
    }
    override suspend fun clearTokens() {
        userPreferences.clearAll()
    }
}

class AuthenticatedOkHttpClient @Inject constructor(
    private val tokenStorage: TokenStorage,
    private val authManager: AuthManager
) {
    fun create(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request()
                val accessToken = runBlocking { tokenStorage.getAccessToken() }
                val authenticatedRequest = if (accessToken != null) {
                    request.newBuilder()
                        .header("Authorization", "Bearer $accessToken")
                        .build()
                } else {
                    request
                }

                // Make the request
                val response = chain.proceed(authenticatedRequest)

                // Handle unauthorized response (401)
                if (response.code == 401) {
                    response.close() // Close the previous response

                    // Attempt to refresh the token
                    val newAccessToken = runBlocking { authManager.refreshAllToken() }
                    if (newAccessToken != null) {
                        // Retry the request with the new token
                        val newRequest = request.newBuilder()
                            .header("Authorization", "Bearer $newAccessToken")
                            .build()
                        return@addInterceptor chain.proceed(newRequest)
                    } else {
                        // Logout the user if the refresh fails
                        runBlocking { authManager.handleDeviceConflict() }
                    }
                }

                response
            }
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }
}

