package com.nabssam.bestbook.data.repository.auth

import com.nabssam.bestbook.domain.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Inject


interface TokenStorage {
    suspend fun getAccessToken(): String?
    suspend fun getRefreshToken(): String?
    suspend fun saveTokens(accessToken: String, refreshToken: String)
    suspend fun clearTokens()
}

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

