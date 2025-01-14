package com.nabssam.bestbook.presentation.ui.account.auth;

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nabssam.bestbook.data.remote.dto.auth.SignUpRequest
import com.nabssam.bestbook.data.repository.AuthRepository
import com.nabssam.bestbook.data.repository.ExamRepository
import com.nabssam.bestbook.data.repository.UserPreferencesRepository
import com.nabssam.bestbook.presentation.ui.account.auth.util.AuthSteps
import com.nabssam.bestbook.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val examRepository: ExamRepository,
    private val userPreferencesRepository: UserPreferencesRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(AuthState())
    val state = _state.asStateFlow()

    private val _errState = MutableStateFlow<String?>(null)
    val errState = _errState.asStateFlow()

    init {
        AuthEvent.Retry
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.SignIn -> signIn()
            is AuthEvent.SignUp -> signUp()
            is AuthEvent.NavigateBack -> handleNavigateBack()
            is AuthEvent.NavigateNext -> {
                /*if (_state.value.passwrd == _state.value.confirmPassword)
                    _state.value.error = null
                else {
                    _state.value.error = "Password mismatch"
                }
                if (_state.value.error == null)*/ handleNavigateNext()
            }
            is AuthEvent.UpdateUsername -> updateState { it.copy(username = event.username) }
            is AuthEvent.UpdatePassword -> updateState { it.copy(password = event.password) }
            is AuthEvent.UpdateClass -> updateState { it.copy(currentClass = event.className) }
            is AuthEvent.UpdateSchool -> updateState { it.copy(schoolName = event.schoolName) }
            is AuthEvent.UpdateTargetYear -> updateState { it.copy(targetYear = event.year.toIntOrNull() ?: 202) }
            is AuthEvent.UpdateMobile -> updateState { it.copy(mobileNumber = event.mobile) }
            is AuthEvent.UpdateOtp -> updateState { it.copy(otp = event.otp) }
            is AuthEvent.SendOtp -> sendOtp()
            is AuthEvent.VerifyOtp -> verifyOtp()
           // is AuthEvent.ToggleNewUser -> toggleNewUser()
            is AuthEvent.Retry -> fetchAllExam()
            is AuthEvent.UpdateConfirmPassword -> updateState { it.copy(confirmPassword = event.password) }
            is AuthEvent.UpdateUserTargetExam -> updateTargetExam(event.exam)
        }
    }

    private fun updateTargetExam(exam: String) {
        if (_state.value.userTargetExams.contains(exam))
            updateState { it.copy(userTargetExams = _state.value.userTargetExams.minus(exam)) }
        else
            updateState { it.copy(userTargetExams = _state.value.userTargetExams.plus(exam)) }
    }

    private fun checkAuthState() {
        viewModelScope.launch {
            userPreferencesRepository.accessToken.collect { token ->
                _state.value.isSignedIn = false
                //token != null
            }
        }
    }

    private fun fetchAllExam() {
        viewModelScope.launch {
            examRepository.fetchAllTargetExam().collect { resource ->
                _errState.value = null
                when (resource) {
                    is Resource.Error -> {
                        updateState { it.copy(isLoading = false) }
                        _errState.value = resource.message
                    }

                    is Resource.Loading -> {
                        updateState { it.copy(isLoading = true) }
                    }

                    is Resource.Success -> {
                        updateState { it.copy(targetExams = resource.data ?: emptyList(), isLoading = false) }
                    }
                }
            }
        }
    }

    private fun signIn() {
        viewModelScope.launch {
            _errState.value = null
            updateState { it.copy(isLoading = true) }
            authRepository.signIn(_state.value.username, _state.value.password).fold(onSuccess = {
                updateState { it.copy(isLoading = false, isSignedIn = true) }
            }, onFailure = { error ->
                _errState.value = error.message
                updateState { it.copy(isLoading = false) }
            })
        }
    }

    private fun signUp() {
        viewModelScope.launch {
            _errState.value = null
            updateState { it.copy(isLoading = true) }

            val currentState = _state.value
            authRepository.signUp(
                SignUpRequest(
                    currentClass = currentState.currentClass,
                    password = currentState.password,
                    phoneNumber = currentState.mobileNumber,
                    targetExam = currentState.targetExams[0], // TODO: Remove ordinal access
                    targetYear = currentState.targetYear,
                    username = currentState.username
                )
            ).fold(
                onSuccess = {
                    updateState {
                        it.copy(
                            isLoading = false,
                            currentStep = AuthSteps.LOGIN
                        )
                    }
                }, onFailure = { error ->
                    _errState.value = error.message
                    updateState { it.copy(isLoading = false) }
                }
            )
        }
    }

    private fun handleNavigateBack() {
        val currentStep = _state.value.currentStep
        val previousStep = when (currentStep) {
            AuthSteps.LOGIN -> AuthSteps.LOGIN
            AuthSteps.CREDENTIALS -> AuthSteps.LOGIN
            AuthSteps.EDUCATION_INFO -> AuthSteps.CREDENTIALS
            AuthSteps.EXAM_INFO -> AuthSteps.EDUCATION_INFO
            AuthSteps.MOBILE_VERIFICATION -> AuthSteps.EXAM_INFO
        }
        updateState { it.copy(currentStep = previousStep) }
    }

    private fun handleNavigateNext() {
        _errState.value = null
        val currentStep = _state.value.currentStep
        val nextStep = when (currentStep) {
            AuthSteps.LOGIN -> AuthSteps.CREDENTIALS
            AuthSteps.CREDENTIALS -> {
                if (_state.value.password == _state.value.confirmPassword) {
                    AuthSteps.EDUCATION_INFO
                }
                else {
                    _errState.value = "Password mismatch"
                    null
                }
            }
            AuthSteps.EDUCATION_INFO -> AuthSteps.EXAM_INFO
            AuthSteps.EXAM_INFO -> AuthSteps.MOBILE_VERIFICATION
            AuthSteps.MOBILE_VERIFICATION -> {
                if (_state.value.isOtpVerified)
                    AuthSteps.LOGIN
                else {
                    _errState.value = "Incorrect OTP!"
                    null
                }
            }
        }
       // if (_state.value.error == null)
        nextStep?.let { updateState { state -> state.copy(currentStep = it) } }
    }

    private fun sendOtp() {
        viewModelScope.launch {
//            authRepository.
            updateState { it.copy(isLoading = true) }
            // TODO: Implement OTP sending logic
            delay(1000) // Simulating API call
            updateState {
                it.copy(
                    isLoading = false, isOtpSent = true
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
                    isLoading = false, isOtpVerified = true
                )
            }
            // TODO: Handle successful registration
        }
    }

    private fun updateState(update: (AuthState) -> AuthState) {
        _state.value = update(_state.value)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun validateCurrentStep(): Boolean {
        return when (_state.value.currentStep) {
            AuthSteps.LOGIN -> {
                _state.value.username.isNotEmpty() && _state.value.password.isNotEmpty()
            }

            AuthSteps.CREDENTIALS -> {
                _state.value.username.isNotEmpty() && _state.value.password.isNotEmpty() && _state.value.confirmPassword.isNotEmpty()
            }

            AuthSteps.EDUCATION_INFO -> {
                _state.value.currentClass.isNotEmpty() && _state.value.schoolName.isNotEmpty()
            }

            AuthSteps.EXAM_INFO -> {
                _state.value.userTargetExams.isNotEmpty() && (_state.value.targetYear) >= LocalDate.now().year
            }

            AuthSteps.MOBILE_VERIFICATION -> {
                _state.value.mobileNumber.length == 10 && (_state.value.isOtpVerified || (_state.value.isOtpSent && _state.value.otp.length == 6))
            }
        }
    }
}