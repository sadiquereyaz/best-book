package com.nabssam.bestbook.presentation.ui.account.auth
import com.nabssam.bestbook.presentation.ui.account.auth.util.AuthSteps
import com.nabssam.bestbook.utils.Standard

data class AuthState(
    val username: String = "ayaz",
    val password: String = "ayaz",
    val confirmPassword: String = "ayaz",
    var currentClass: String = "",
    val schoolName: String = "AMU Sr. Sec. School",
    var standardList: List<Standard> = emptyList(),
    val allTargetExam: List<String> = emptyList(),
    var userTargetExams: List<String> = emptyList(),
    val targetYear: Int = 2026,     // TODO: change to 202
    val mobileNumber: String = "8745971753",
    val otp: String = "",
    val currentStep: AuthSteps = AuthSteps.LOGIN,
    val isLoading: Boolean = false,
    val isOtpSent: Boolean = false,
//    val isOtpVerified: Boolean = false,
    var isSignedIn: Boolean = false,
)