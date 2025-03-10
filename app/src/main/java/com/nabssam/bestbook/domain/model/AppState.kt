package com.nabssam.bestbook.domain.model

sealed class AppState {
    object Initial : AppState()
    object DeviceConflict : AppState()
    object TokenExpired : AppState()
    object LoggedOut : AppState()
    object NetworkError : AppState()
    data object Authenticated : AppState()
    object Unauthenticated : AppState()
    data object AlreadySignedIn : AppState()
}
