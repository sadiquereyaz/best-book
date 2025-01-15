package com.nabssam.bestbook.data.repository.auth

import com.nabssam.bestbook.data.remote.api.AuthApiService
import com.nabssam.bestbook.domain.model.AppState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

interface TokenStorage {
    suspend fun getAccessToken(): String?
    suspend fun getRefreshToken(): String?
    suspend fun saveTokens(accessToken: String, refreshToken: String)
    suspend fun clearTokens()
}

class AuthManager @Inject constructor(
    private val tokenStorage: TokenStorage,
    private val authApiService: AuthApiService
) {

    private val _authEvents = MutableSharedFlow<AppState>()
    val authEvents = _authEvents.asSharedFlow()

    suspend fun refreshAllToken(): String? {
        val refreshToken = tokenStorage.getRefreshToken()
        if (refreshToken.isNullOrBlank()) return null

        return try {
            val response = authApiService.getNewTokens(refreshToken)
            if (response.isSuccessful) {
                response.body()?.let {
                    tokenStorage.saveTokens(
                        accessToken = it.accessToken,
                        refreshToken = it.refreshToken
                    )
                    return it.accessToken
                }
            }
            null
        } catch (e: Exception) {
            null
        }
    }

    suspend fun handleDeviceConflict() {
        logout()
        _authEvents.emit(AppState.DeviceConflict) // Notify the UI about device conflict
    }

    private suspend fun logout() {
        tokenStorage.clearTokens()
        _authEvents.emit(AppState.LoggedOut) // Emit logout event
    }
}

// 3. Modify AuthInterceptor to use the interface instead
