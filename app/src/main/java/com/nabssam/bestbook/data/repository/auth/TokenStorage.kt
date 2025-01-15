package com.nabssam.bestbook.data.repository.auth

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

// 3. Create a separate AuthenticatedOkHttpClient for authenticated requests
class AuthenticatedOkHttpClient @Inject constructor(
    private val tokenStorage: TokenStorage
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
                chain.proceed(authenticatedRequest)
            }
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }
}

