package com.nabssam.bestbook.presentation.ui.account.auth
import com.nabssam.bestbook.presentation.ui.account.auth.util.AuthSteps
import com.nabssam.bestbook.utils.Standard

data class AuthState(
    val username: String = "sadique",
    val password: String = "sadique",
    val confirmPassword: String = "sadique",
    var currentClass: String = "",
    val schoolName: String = "AMU Sr. Sec. School",
    var standardList: List<Standard> = emptyList(),
    val allTargetExam: List<String> = emptyList(),
    var userTargetExams: List<String> = emptyList(),
    val targetYear: Int = 2026,     // TODO: change to 202
    val mobileNumber: String = "8745971753",
    val otp: String = "",
    val currentStep: AuthSteps = AuthSteps.LOGIN,
    val isLoading: Boolean = true,
    val isOtpSent: Boolean = false,
//    val isOtpVerified: Boolean = false,
    var isSignedIn: Boolean = false,
)