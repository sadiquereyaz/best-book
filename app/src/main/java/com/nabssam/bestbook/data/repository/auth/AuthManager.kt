package com.nabssam.bestbook.data.repository.auth

import com.nabssam.bestbook.data.remote.api.AuthApiService
import com.nabssam.bestbook.domain.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

// Auth manager to handle authentication state
interface AuthTokenProvider {
    suspend fun getAccessToken(): String?
    suspend fun refreshToken(): String?
    suspend fun handleDeviceConflict()
    suspend fun logout()
}

// 2. Modify AuthManager to implement the interface
class AuthManager @Inject constructor(
    private val userPreferences: UserPreferencesRepository,
    private val authApiService: AuthApiService
) : AuthTokenProvider {
    private val _authState = MutableStateFlow<AuthState>(AuthState.Unauthenticated)
    val authState = _authState.asStateFlow()

    private val _authEvents = MutableSharedFlow<AuthEvent>()
    val authEvents = _authEvents.asSharedFlow()

    override suspend fun getAccessToken(): String? {
        return userPreferences.accessToken.firstOrNull()
    }

    override suspend fun refreshToken(): String? {
        val refreshToken = userPreferences.refreshToken.firstOrNull()
        return try {
            val response = authApiService.refreshToken(refreshToken ?: "default_datastore_token")
            if (response.isSuccessful) {
                response.body()?.let {
                    userPreferences.saveTokens(
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

    override suspend fun handleDeviceConflict() {
        logout()
        // You can use a shared flow or event bus to notify the UI
        _authEvents.emit(AuthEvent.DeviceConflict)
    }

    override suspend fun logout() {
        userPreferences.clearAll()
    }
}

// 3. Modify AuthInterceptor to use the interface instead
