package com.nabssam.bestbook.presentation.ui.sample
/*

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SignUpViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<SignUpUiState>(SignUpUiState.Idle(IdleScreen()))
    val uiState = _uiState.asStateFlow()

    fun onUsernameChange(newUsername: String) {
        val currentState = _uiState.value
        if (currentState is SignUpUiState.Idle) {
            _uiState.value = SignUpUiState.Idle(currentState.data.copy(username = newUsername))
        }
    }

    fun onPasswordChange(newPassword: String) {
        val currentState = _uiState.value
        if (currentState is SignUpUiState.Idle) {
            _uiState.value = SignUpUiState.Idle(currentState.data.copy(password = newPassword))
        }
    }

    fun onTermsAcceptedChange(accepted: Boolean) {
        val currentState = _uiState.value
        if (currentState is SignUpUiState.Idle) {
            _uiState.value = SignUpUiState.Idle(currentState.data.copy(termsAccepted = accepted))
        }
    }

    fun signUp() {
        val currentState = _uiState.value
        if (currentState is SignUpUiState.Idle) {
            val data = currentState.data
            if (data.username.isBlank() || data.password.isBlank() || !data.termsAccepted) {
                _uiState.value = SignUpUiState.Error("Please fill all fields and accept the terms.")
                return
            }
            
            // Set state to Loading
            _uiState.value = SignUpUiState.Loading

            // Simulate a network call
            viewModelScope.launch {
                delay(2000) // Simulating network delay
                if (data.username == "error") {
                    // Set state to Error if there's an error
                    _uiState.value = SignUpUiState.Error("Sign up failed. Please try again.")
                } else {
                    // Set state to Idle on success or completion
                    _uiState.value = SignUpUiState.Idle(IdleScreen())
                }
            }
        }
    }
}*/
