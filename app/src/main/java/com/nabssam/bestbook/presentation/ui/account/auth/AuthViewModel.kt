package com.nabssam.bestbook.presentation.ui.account.auth;

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nabssam.bestbook.data.remote.dto.auth.SignUpRequest
import com.nabssam.bestbook.data.repository.AuthRepository
import com.nabssam.bestbook.data.repository.ExamRepository
import com.nabssam.bestbook.data.repository.UserPreferencesRepository
import com.nabssam.bestbook.presentation.ui.account.auth.util.RegistrationStep
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

    init {
        AuthEvent.Retry
    }

    fun onEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.SignIn -> signIn()
            is AuthEvent.SignUp -> signUp()
            is AuthEvent.NavigateBack -> handleNavigateBack()
            is AuthEvent.NavigateNext -> handleNavigateNext()
            is AuthEvent.UpdateUsername -> updateState { it.copy(username = event.username) }
            is AuthEvent.UpdatePassword -> updateState { it.copy(password = event.password) }
            is AuthEvent.UpdateClass -> updateState { it.copy(currentClass = event.className) }
            is AuthEvent.UpdateSchool -> updateState { it.copy(schoolName = event.schoolName) }
            is AuthEvent.UpdateTargetYear -> updateState { it.copy(targetYear = event.year) }
            is AuthEvent.UpdateMobile -> updateState { it.copy(mobileNumber = event.mobile) }
            is AuthEvent.UpdateOtp -> updateState { it.copy(otp = event.otp) }
            is AuthEvent.SendOtp -> sendOtp()
            is AuthEvent.VerifyOtp -> verifyOtp()
            is AuthEvent.ToggleNewUser -> toggleNewUser()
            is AuthEvent.Retry -> fetchAllExam()
            is AuthEvent.UpdateConfirmPassword -> updateState { it.copy(confirmPassword = event.password) }
            is AuthEvent.UpdateUserTargetExam -> updateTargetExam(event.exam)
        }
    }

    private fun updateTargetExam(exam: String) {
        Log.d("AUTH_VM", exam)
        if (_state.value.userTargetExams.contains(exam))
            _state.value =
                _state.value.copy(userTargetExams = _state.value.userTargetExams.minus(exam))
            else
            _state.value =
                _state.value.copy(userTargetExams = _state.value.userTargetExams.plus(exam))

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
                when (resource) {
                    is Resource.Error -> updateState {
                        it.copy(
                            error = resource.message,
                            isLoading = false
                        )
                    }

                    is Resource.Loading -> {/*updateState { it.copy(isLoading = true) }*/
                    }

                    is Resource.Success -> {
                        updateState {
                            it.copy(
                                targetExams = resource.data ?: emptyList(),
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }

    private fun signIn() {
        viewModelScope.launch {
            updateState {
                it.copy(
                    isLoading = true, error = null
                )
            }
            authRepository.signIn(_state.value.username, _state.value.password).fold(onSuccess = {
                updateState { it.copy(isLoading = false, error = null, isSignedIn = true) }
            }, onFailure = { error ->
                updateState { it.copy(isLoading = false, error = error.message) }
            })
        }
    }

    private fun signUp() {
        viewModelScope.launch {
            updateState { it.copy(isLoading = true, error = null) }

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
                            error = null,
                            currentStep = RegistrationStep.LOGIN
                        )
                    }
                }, onFailure = { error ->
                    updateState { it.copy(isLoading = false, error = error.message) }
                }
            )
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
            RegistrationStep.CREDENTIALS -> {
                if (_state.value.password == _state.value.confirmPassword) RegistrationStep.EDUCATION_INFO else {
                    _state.value.error = "Password mismatch"
                    RegistrationStep.EDUCATION_INFO
                }
            }

            RegistrationStep.EDUCATION_INFO -> RegistrationStep.EXAM_INFO
            RegistrationStep.EXAM_INFO -> RegistrationStep.MOBILE_VERIFICATION
            RegistrationStep.MOBILE_VERIFICATION -> null
        }
        nextStep?.let { updateState { state -> state.copy(currentStep = it) } }
    }

    private fun addExam(exam: String) {
        Log.d("Add:", exam)
        updateState {
            it.copy(userTargetExams = (it.userTargetExams).plus(exam))
        }
    }

    private fun removeExam(exam: String) {
        updateState {
            Log.d("Remove:", exam)
            it.copy(targetExams = it.userTargetExams.minus(exam))
        }
    }

    private fun sendOtp() {
        viewModelScope.launch {
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
                    isLoading = false, isOtpVerified = true, error = null
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

    private fun updateState(update: (AuthState) -> AuthState) {
        _state.value = update(_state.value)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun validateCurrentStep(): Boolean {
        return when (_state.value.currentStep) {
            RegistrationStep.LOGIN -> {
                _state.value.username.isNotEmpty() && _state.value.password.isNotEmpty()
            }

            RegistrationStep.CREDENTIALS -> {
                _state.value.username.isNotEmpty() && _state.value.password.isNotEmpty() && _state.value.confirmPassword.isNotEmpty()
            }

            RegistrationStep.EDUCATION_INFO -> {
                _state.value.currentClass.isNotEmpty() && _state.value.schoolName.isNotEmpty()
            }

            RegistrationStep.EXAM_INFO -> {
                _state.value.userTargetExams.isNotEmpty() && (_state.value.targetYear) >= LocalDate.now().year
            }

            RegistrationStep.MOBILE_VERIFICATION -> {
                _state.value.mobileNumber.length == 10 && (_state.value.isOtpVerified || (_state.value.isOtpSent && _state.value.otp.length == 6))
            }
        }
    }
}