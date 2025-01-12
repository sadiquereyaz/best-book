package com.nabssam.bestbook.presentation.ui.account.auth

sealed class AuthStateOld {
    data object Initial : AuthStateOld()
    data object Loading : AuthStateOld()
    data object Success : AuthStateOld()
    data class Error(val message: String) : AuthStateOld()
}
