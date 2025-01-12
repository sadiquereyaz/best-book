package com.nabssam.bestbook.presentation.ui.account.auth.claud;

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nabssam.bestbook.presentation.ui.account.auth.AuthState
import com.nabssam.bestbook.presentation.ui.account.auth.claud.util.RegistrationStep
import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import kotlin.math.sign

@HiltViewModel
class RegistrationViewModel @Inject
constructor() : ViewModel() {
    private val _state = MutableStateFlow(RegistrationState())
    val state = _state.asStateFlow()

    fun onEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.SignIn ->  sign()
            is AuthEvent.NavigateBack -> handleNavigateBack()
            is AuthEvent.NavigateNext -> handleNavigateNext()
            is AuthEvent.UpdateUsername -> updateState { it.copy(username = event.username) }
            is AuthEvent.UpdatePassword -> updateState { it.copy(password = event.password) }
            is AuthEvent.UpdateClass -> updateState { it.copy(currentClass = event.className) }
            is AuthEvent.UpdateSchool -> updateState { it.copy(schoolName = event.schoolName) }
            is AuthEvent.AddExam -> addExam(event.exam)
            is AuthEvent.RemoveExam -> removeExam(event.exam)
            is AuthEvent.UpdateTargetYear -> updateState { it.copy(targetYear = event.year) }
            is AuthEvent.UpdateMobile -> updateState { it.copy(mobileNumber = event.mobile) }
            is AuthEvent.UpdateOtp -> updateState { it.copy(otp = event.otp) }
            is AuthEvent.SendOtp -> sendOtp()
            is AuthEvent.VerifyOtp -> verifyOtp()
            is AuthEvent.ToggleNewUser -> toggleNewUser()
        }
    }

    private fun sign() {
        viewModelScope.launch {
            viewModelScope.launch {
                updateState { it.copy(
                    isLoading = true,
                    error = null
                ) }
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
    }

    private fun handleNavigateBack() {
        val currentStep = _state.value.currentStep
        val previousStep = when (currentStep) {
            RegistrationStep.LOGIN -> RegistrationStep.LOGIN
            RegistrationStep.CREDENTIALS -> RegistrationStep.LOGIN
            RegistrationStep.EDUCATION_INFO -> RegistrationStep.CREDENTIALS
            RegistrationStep.EXAM_INFO -> RegistrationStep.EDUCATION_INFO
            RegistrationStep.MOBILE_VERIFICATION -> RegistrationStep.EXAM_INFO
        }
        updateState { it.copy(currentStep = previousStep) }
    }

    private fun handleNavigateNext() {
        val currentStep = _state.value.currentStep
        val nextStep = when (currentStep) {
            RegistrationStep.LOGIN -> RegistrationStep.CREDENTIALS
            RegistrationStep.CREDENTIALS -> RegistrationStep.EDUCATION_INFO
            RegistrationStep.EDUCATION_INFO -> RegistrationStep.EXAM_INFO
            RegistrationStep.EXAM_INFO -> RegistrationStep.MOBILE_VERIFICATION
            RegistrationStep.MOBILE_VERIFICATION -> null
        }
        nextStep?.let { updateState { state -> state.copy(currentStep = it) } }
    }

    private fun addExam(exam: String) {
        updateState { 
            it.copy(targetExams = it.targetExams + exam)
        }
    }

    private fun removeExam(exam: String) {
        updateState { 
            it.copy(targetExams = it.targetExams - exam)
        }
    }

    private fun sendOtp() {
        viewModelScope.launch {
            updateState { it.copy(isLoading = true) }
            // TODO: Implement OTP sending logic
            delay(1000) // Simulating API call
            updateState { 
                it.copy(
                    isLoading = false,
                    isOtpSent = true
                )
            }
        }
    }

    private fun verifyOtp() {
        viewModelScope.launch {
            updateState { it.copy(isLoading = true) }
            // TODO: Implement OTP verification logic
            delay(1000) // Simulating API call
            updateState {
                it.copy(
                        isLoading = false,
                        isOtpVerified = true,
                        error = null
                )
            }
            // TODO: Handle successful registration
        }
    }

    private fun toggleNewUser() {
        updateState {
            it.copy(
                    currentStep = RegistrationStep.CREDENTIALS,
                    //username = "",
                    //password = ""
            )
        }
    }

    private fun updateState(update: (RegistrationState) -> RegistrationState) {
        _state.value = update(_state.value)
    }

    @RequiresApi(Build.VERSION_CODES.O)
     fun validateCurrentStep(): Boolean {
        return when (_state.value.currentStep) {
            RegistrationStep.LOGIN -> {
                _state.value.username.isNotEmpty() && _state.value.password.isNotEmpty()
            }
            RegistrationStep.CREDENTIALS -> {
                _state.value.username.isNotEmpty() && _state.value.password.isNotEmpty()
            }
            RegistrationStep.EDUCATION_INFO -> {
                _state.value.currentClass.isNotEmpty() && _state.value.schoolName.isNotEmpty()
            }
            RegistrationStep.EXAM_INFO -> {
                _state.value.targetExams.isNotEmpty() && (_state.value.targetYear).toInt() > LocalDate.now().year
            }
            RegistrationStep.MOBILE_VERIFICATION -> {
                _state.value.mobileNumber.length == 10 &&
                        (_state.value.isOtpVerified || (_state.value.isOtpSent && _state.value.otp.length == 6))
            }
        }
    }
}