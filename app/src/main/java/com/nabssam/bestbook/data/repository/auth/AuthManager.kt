package com.nabssam.bestbook.data.repository.auth

import android.util.Log
import com.nabssam.bestbook.domain.model.AppState
import com.nabssam.bestbook.utils.helper.PDFDownloadStatusHelper
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject


class AuthManager @Inject constructor(
    private val tokenStorage: TokenStorage,
    private val pdfStorage: PDFDownloadStatusHelper
    //private val authApiService: AuthApiService
) {

    private val _authEvents = MutableSharedFlow<AppState>()
    val authEvents = _authEvents.asSharedFlow()

    suspend fun refreshAllToken(): String? {
        val refreshToken = tokenStorage.getRefreshToken()
        if (refreshToken.isNullOrBlank()) return null

        return try {
           /* val response = authApiService.getNewTokens(refreshToken)
            if (response.isSuccessful) {
                response.body()?.let {
                    tokenStorage.saveTokens(
                        accessToken = it.accessToken,
                        refreshToken = it.refreshToken
                    )
                    return it.accessToken
                }
            }*/
            null
        } catch (e: Exception) {
            null
        }
    }

    suspend fun handleDeviceConflict() {
        logout()
            //_authEvents.emit(AppState.DeviceConflict) // Notify the UI about device conflict
    }

     suspend fun isSignedIn() {
         Log.d("APP_STATE", "isSignedIn function calling")
        if(tokenStorage.getAccessToken() != null)
            _authEvents.emit(AppState.AlreadySignedIn)
         Log.d("APP_STATE", "token acc: ${tokenStorage.getAccessToken()} \n token refresh: ${tokenStorage.getRefreshToken()}")
    }

    private suspend fun logout() {
        Log.d("APP_STATE", "logout function calling")
        pdfStorage.deleteAllDownloadedPDFs()
        tokenStorage.clearTokens()
        _authEvents.emit(AppState.LoggedOut) // Emit logout event
    }
}
