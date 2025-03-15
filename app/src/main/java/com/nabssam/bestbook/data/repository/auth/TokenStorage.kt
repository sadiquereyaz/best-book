package com.nabssam.bestbook.data.repository.auth

import com.nabssam.bestbook.domain.repository.UserDataStoreRepository
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject


interface TokenStorage {
    suspend fun getAccessToken(): String?
    suspend fun getRefreshToken(): String?
    suspend fun saveTokens(accessToken: String, refreshToken: String)
    suspend fun clearTokens()
}

class UserPreferencesTokenStorage @Inject constructor(
    private val userPreferences: UserDataStoreRepository
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

