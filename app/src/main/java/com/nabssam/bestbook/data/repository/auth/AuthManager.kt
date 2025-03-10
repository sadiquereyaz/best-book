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
) {

    private val _apptate = MutableSharedFlow<AppState>()
    val appState = _apptate.asSharedFlow()


    suspend fun logout() {
        Log.d("APP_STATE", "logout function calling")
//        pdfStorage.deleteAllDownloadedPDFs()
//        tokenStorage.clearTokens()
//        delay(100)
        _apptate.emit(AppState.LoggedOut) // Emit logout event
    }
}
