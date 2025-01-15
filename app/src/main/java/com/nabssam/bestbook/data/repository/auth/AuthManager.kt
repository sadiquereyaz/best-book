package com.nabssam.bestbook.data.repository.auth

import AuthEvent
import com.nabssam.bestbook.data.remote.api.AuthApiService
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
//    private val _authState = MutableStateFlow<AuthState>(AuthState.Unauthenticated)
//    val authState = _authState.asStateFlow()

    private val _authEvents = MutableSharedFlow<AuthEvent>()
    val authEvents = _authEvents.asSharedFlow()

    suspend fun refreshToken(): String? {
        val refreshToken = tokenStorage.getRefreshToken()
        return try {
            val response = authApiService.refreshToken(refreshToken ?: "default_datastore_token")
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
        // _authEvents.emit(AuthEvent.DeviceConflict)
    }

    suspend fun logout() {
        tokenStorage.clearTokens()
    }
}

// 3. Modify AuthInterceptor to use the interface instead
