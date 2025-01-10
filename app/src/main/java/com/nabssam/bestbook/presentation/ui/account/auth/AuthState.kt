package com.nabssam.bestbook.presentation.ui.account.auth

sealed class AuthState {
    data object Initial : AuthState()
    data object Loading : AuthState()
    data object Success : AuthState()
    data class Error(val message: String) : AuthState()
}
