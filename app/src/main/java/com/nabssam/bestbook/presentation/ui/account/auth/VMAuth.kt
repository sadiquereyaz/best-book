package com.nabssam.bestbook.presentation.ui.account.auth;

import android.icu.util.Calendar
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.ListenableWorker.Result.retry
import com.nabssam.bestbook.data.remote.dto.auth.SignUpRequest
import com.nabssam.bestbook.data.remote.dto.auth.VerifyOtpRequest
import com.nabssam.bestbook.data.repository.auth.AuthRepository
import com.nabssam.bestbook.data.repository.ExamRepository
import com.nabssam.bestbook.data.repository.auth.UserPreferencesTokenStorage
import com.nabssam.bestbook.presentation.ui.account.auth.util.AuthSteps
import com.nabssam.bestbook.presentation.ui.snackbar.SnackbarDuration
import com.nabssam.bestbook.presentation.ui.snackbar.SnackbarManager
import com.nabssam.bestbook.presentation.ui.snackbar.SnackbarMessage
import com.nabssam.bestbook.presentation.ui.snackbar.SnackbarType
import com.nabssam.bestbook.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class VMAuth @Inject constructor(
    private val authRepository: AuthRepository,
    private val examRepository: ExamRepository,
    private val tokenStorage: UserPreferencesTokenStorage,
    private val snackbarManager: SnackbarManager
) : ViewModel() {
    private val _state = MutableStateFlow(AuthState(isLoading = true))
    val state = _state.asStateFlow()

    private val _errState = MutableStateFlow<String?>(null)
    val errState = _errState.asStateFlow()

    init {
        checkAuthState()
        onEvent(AuthEvent.Initialize)
        /*viewModelScope.launch {
            delay(2_000)
            state.value.isOtpVerified = true
            Log.d("AUTH_VM", "init: ${_state.value}")
        }*/
    }

    fun onEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.SignIn -> signIn()
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
            is AuthEvent.OnClassSelect -> updateState {
                it.copy(
                    currentClass = event.selectedClass,
                    allTargetExam = event.classExams
                )
            }

            is AuthEvent.UpdateSchool -> updateState { it.copy(schoolName = event.schoolName) }
            is AuthEvent.UpdateTargetYear -> updateState {
                it.copy(
                    targetYear = event.year.toIntOrNull() ?: 202
                )
            }

            is AuthEvent.UpdateMobile -> updateState { it.copy(mobileNumber = event.mobile) }
            is AuthEvent.UpdateOtp -> updateState { it.copy(otp = event.otp) }
            is AuthEvent.RegisterAndSendOtp -> registerAndSendOtp()
            is AuthEvent.VerifyOtp -> verifyOtp()
            // is AuthEvent.ToggleNewUser -> toggleNewUser()
            is AuthEvent.Initialize -> fetchAllExam()
            is AuthEvent.UpdateConfirmPassword -> updateState { it.copy(confirmPassword = event.password) }
            is AuthEvent.UpdateUserTargetExam -> updateTargetExam(event.exam)
            AuthEvent.ForgetPassword -> {}
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
            if (tokenStorage.getAccessToken() != null) {
                //Log.d("AUTH_VM", "checkAuthState: ${tokenStorage.getAccessToken()}")
                updateState { it.copy(isSignedIn = true, isLoading = false) }
//                Log.d("AUTH_VM", "checkAuthState: ${_state.value}")
            } else {
                //Log.d("AUTH_VM", "checkAuthState: null")
                updateState { it.copy(isLoading = false) }
            }
        }
    }

    private fun fetchAllExam() {
        viewModelScope.launch {
            _errState.value = null
            examRepository.fetchAllStandard().collect { resource ->
                when (resource) {
                    is Resource.Error -> {
                        updateState { it.copy(isLoading = false) }
                        _errState.value = resource.message
                    }

                    is Resource.Loading -> {
                        updateState { it.copy(isLoading = true) }
                    }

                    is Resource.Success -> {
                        updateState {
                            it.copy(
                                standardList = resource.data ?: emptyList(),
                                isLoading = false
                            )
                        }
                        ////Log.d("AUTH_VM", "success fetchAllExam: ${_state.value.standardList}")
                    }
                }
            }
        }
    }

    private fun signIn() {
        viewModelScope.launch {
            _errState.value = null
            Log.d("AUTH_VM", "signIn: ${_state.value}")
            snackbarManager.showSnackbar(
                SnackbarMessage(
                    message = "Sign in click",
                    type = SnackbarType.ERROR,
                    duration = SnackbarDuration.Long,
                    actionLabel = "Retry",
                    onActionPerformed = { retry() }
                )
            )
            /*updateState { it.copy(isLoading = true) }
            authRepository.signIn(_state.value.username, _state.value.password).fold(onSuccess = {
                updateState { it.copy(isLoading = false, isSignedIn = true) }
            }, onFailure = { error ->
                _errState.value = error.message
                updateState { it.copy(isLoading = false) }
            })*/
        }
    }

    private fun registerAndSendOtp() {

        viewModelScope.launch {
            _errState.value = null
            updateState { it.copy(isLoading = true) }
            val currentState = _state.value
            authRepository.signUp(
                SignUpRequest(
                    currentClass = currentState.currentClass,
                    password = currentState.password,
                    phoneNumber = currentState.mobileNumber,
                    targetExam = currentState.userTargetExams,
                    targetYear = currentState.targetYear.toString(),
                    username = currentState.username,
//                    school = currentState.schoolName
                )
            ).fold(
                onSuccess = {
                    updateState {
                        it.copy(
                            isLoading = false,
                            isOtpSent = true
                            //currentStep = AuthSteps.LOGIN
                        )
                    }
                },
                onFailure = { error ->
                    _errState.value = error.message
                    updateState { it.copy(isLoading = false) }
                }
            )
        }
    }

    private fun verifyOtp() {
        viewModelScope.launch {
            _errState.value = null
            updateState { it.copy(isLoading = true) }
            authRepository.verifyOtp(
                VerifyOtpRequest(otp = _state.value.otp.toInt(), phone = _state.value.mobileNumber)
            ).fold(
                onSuccess = {
                    Log.d("AUTH_VM", "verifyOtp success: $it")
                    updateState {
                        it.copy(
                            isLoading = false,
                            isOtpVerified = true,
                            currentStep = AuthSteps.LOGIN
                        )
                    }
                },
                onFailure = { error ->
                    Log.d("AUTH_VM", "verifyOtp fail: $error")
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
                } else {
                    _errState.value = "Password mismatch"
                    null
                }
            }

            AuthSteps.EDUCATION_INFO -> AuthSteps.EXAM_INFO
            AuthSteps.EXAM_INFO -> AuthSteps.MOBILE_VERIFICATION
            AuthSteps.MOBILE_VERIFICATION -> {
//                if (_state.value.isOtpVerified)
                AuthSteps.LOGIN
//                else {
//                    _errState.value = "Incorrect OTP!"
//                    null
//                }
            }
        }
        // if (_state.value.error == null)
        nextStep?.let { updateState { state -> state.copy(currentStep = it) } }
    }

    private fun updateState(update: (AuthState) -> AuthState) {
        _state.value = update(_state.value)
    }

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
                _state.value.userTargetExams.isNotEmpty() && (_state.value.targetYear) >= getCurrentYearCalendar()
            }

            AuthSteps.MOBILE_VERIFICATION -> {
                _state.value.mobileNumber.length == 10 && (/*_state.value.isOtpVerified ||*/ (_state.value.isOtpSent && _state.value.otp.length == 4))
            }
        }
    }

    private fun getCurrentYearCalendar(): Int {
        val calendar = Calendar.getInstance()
        return calendar.get(Calendar.YEAR)
    }
}