package com.nabssam.bestbook.presentation.ui.account.auth


sealed class AuthEvent {
    data object SignIn : AuthEvent()
    data object NavigateBack : AuthEvent()
    data object NavigateNext : AuthEvent()
    data class UpdateUsername(val username: String) : AuthEvent()
    data class UpdatePassword(val password: String) : AuthEvent()
    data class UpdateConfirmPassword(val password: String) : AuthEvent()
    data class OnClassSelect(val selectedClass: String, val classExams: List<String>) : AuthEvent()
    data class UpdateSchool(val schoolName: String) : AuthEvent()
    data class UpdateUserTargetExam(val exam: String) : AuthEvent()
    data class UpdateTargetYear(val year: String) : AuthEvent()
    data class UpdateMobile(val mobile: String) : AuthEvent()
    data class UpdateOtp(val otp: String) : AuthEvent()
    data object Initialize:AuthEvent()
    data object RegisterAndSendOtp : AuthEvent()
    data object VerifyOtp : AuthEvent()
    //data object ToggleNewUser : AuthEvent()
    data object ForgetPassword : AuthEvent()

}