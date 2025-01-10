package com.nabssam.bestbook.presentation.ui.account.auth

import com.nabssam.bestbook.domain.model.AuthData

sealed class AuthState {
    data object Initial : AuthState()
    data object Loading : AuthState()
    data class Success(val authData: AuthData) : AuthState()
    data class Error(val message: String) : AuthState()
}
