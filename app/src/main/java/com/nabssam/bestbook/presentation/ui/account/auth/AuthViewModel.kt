package com.nabssam.bestbook.presentation.ui.account.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nabssam.bestbook.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Initial)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            authRepository.signIn(email, password).fold(
                onSuccess = {
                    _authState.value = AuthState.Success
                },
                onFailure = { error ->
                    _authState.value = AuthState.Error(error.message ?: "Sign in failed")
                }
            )
        }
    }
    
    fun register(name: String, email: String, password: String, phone: String?) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            authRepository.register(name, email, password, phone).fold(
                onSuccess = {
                    _authState.value = AuthState.Success
                },
                onFailure = { error ->
                    _authState.value = AuthState.Error(error.message ?: "Registration failed")
                }
            )
        }
    }
}