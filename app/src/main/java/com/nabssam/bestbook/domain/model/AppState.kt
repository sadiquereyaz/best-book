package com.nabssam.bestbook.domain.model

sealed class AppState {
    object DeviceConflict : AppState()
    object TokenExpired : AppState()
    object LoggedOut : AppState()
    object NetworkError : AppState()
    object Authenticated : AppState()
    object Unauthenticated : AppState()
}
