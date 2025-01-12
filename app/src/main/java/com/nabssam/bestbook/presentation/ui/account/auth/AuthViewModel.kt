package com.nabssam.bestbook.presentation.ui.account.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nabssam.bestbook.data.repository.AuthRepository
import com.nabssam.bestbook.data.repository.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Loading)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    init {
        checkAuthState()
    }

    private fun checkAuthState() {
        viewModelScope.launch {
            userPreferencesRepository.accessToken.collect { token ->
                _authState.value = if (token != null) AuthState.Success else AuthState.Initial
            }
        }
    }

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

    fun register(username: String, email: String, password: String, phone: String?) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            authRepository.register(username, email, password, phone).fold(
                onSuccess = {
                    _authState.value = AuthState.Success
                },
                onFailure = { error ->
                    _authState.value = AuthState.Error(error.message ?: "Registration failed")
                }
            )
        }
    }

    fun onEvent(event: EventAuth) {

        when (event) {
            is EventAuth.SignIn -> {
                signIn(event.username, event.password)
            }

            is EventAuth.Register -> {
                //register(event.username, event.password, event.phone, )
            }

            EventAuth.FetchExams -> TODO()
            is EventAuth.ForgetPass -> TODO()
            EventAuth.Retry -> TODO()
            is EventAuth.VerifyOtp -> TODO()
        }
    }
}