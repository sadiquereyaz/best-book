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
class AuthViewModelOld @Inject constructor(
    private val authRepository: AuthRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val _authStateOld = MutableStateFlow<AuthStateOld>(AuthStateOld.Loading)
    val authStateOld: StateFlow<AuthStateOld> = _authStateOld.asStateFlow()

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            _authStateOld.value = AuthStateOld.Loading
            authRepository.signIn(email, password).fold(
                onSuccess = {
                    _authStateOld.value = AuthStateOld.Success
                },
                onFailure = { error ->
                    _authStateOld.value = AuthStateOld.Error(error.message ?: "Sign in failed")
                }
            )
        }
    }

    fun register(username: String, email: String, password: String, phone: String?) {
        viewModelScope.launch {
            _authStateOld.value = AuthStateOld.Loading
            /*authRepository.signUp(username, email, password, phone, username).fold(
                onSuccess = {
                    _authStateOld.value = AuthStateOld.Success
                },
                onFailure = { error ->
                    _authStateOld.value = AuthStateOld.Error(error.message ?: "Registration failed")
                }
            )*/
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