package com.nabssam.bestbook.data.repository.auth

import com.nabssam.bestbook.data.remote.api.AuthApiService
import com.nabssam.bestbook.domain.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

// Auth manager to handle authentication state
class AuthManager @Inject constructor(
    private val userPreferences: UserPreferencesRepository,
    private val authApiService: AuthApiService
) {
    private val _authState = MutableStateFlow<AuthState>(AuthState.Unauthenticated)
    val authState = _authState.asStateFlow()

    private val _authEvents = MutableSharedFlow<AuthEvent>()
    val authEvents = _authEvents.asSharedFlow()

    suspend fun refreshToken(): String? {
        val refreshToken = userPreferences.refreshToken.firstOrNull()
        return try {
            val response = authApiService.refreshToken(refreshToken ?: "default_datastore_token")
            if (response.isSuccessful){
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

    suspend fun handleDeviceConflict() {
        // Clear tokens and notify UI about forced logout
        logout()
        // You can use a shared flow or event bus to notify the UI
        _authEvents.emit(AuthEvent.DeviceConflict)
    }

    suspend fun logout() {
        userPreferences.clearAll()
        // Additional cleanup as needed
    }
}