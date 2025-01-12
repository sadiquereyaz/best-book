package com.nabssam.bestbook.presentation.ui.account.auth.claud

sealed class AuthEvent {
    data object SignIn : AuthEvent()
    data object SignUp : AuthEvent()
    data object NavigateBack : AuthEvent()
    data object NavigateNext : AuthEvent()
    data class UpdateUsername(val username: String) : AuthEvent()
    data class UpdatePassword(val password: String) : AuthEvent()
    data class UpdateClass(val className: String) : AuthEvent()
    data class UpdateSchool(val schoolName: String) : AuthEvent()
    data class AddExam(val exam: String) : AuthEvent()
    data class RemoveExam(val exam: String) : AuthEvent()
    data class UpdateTargetYear(val year: String) : AuthEvent()
    data class UpdateMobile(val mobile: String) : AuthEvent()
    data class UpdateOtp(val otp: String) : AuthEvent()
    data object SendOtp : AuthEvent()
    data object VerifyOtp : AuthEvent()
    data object ToggleNewUser : AuthEvent()
}