package com.nabssam.bestbook.presentation.ui.account.auth
import com.nabssam.bestbook.presentation.ui.account.auth.util.AuthSteps
import com.nabssam.bestbook.utils.DummyData

data class AuthState(
    val username: String = "ayaz",
    val password: String = "ayaz",
    val confirmPassword: String = "11111111",
    val currentClass: String = "XI-PCM",
    val schoolName: String = "AMU Sr. Sec. School",
    val targetExams: List<String> = emptyList(),
    val classes: List<String> = DummyData.targetExamList,
    var userTargetExams: List<String> = emptyList(),
    val targetYear: Int = 2026,     // TODO: change to 202
    val mobileNumber: String = "9506198939",
    val otp: String = "",
    val currentStep: AuthSteps = AuthSteps.LOGIN,
    val isLoading: Boolean = false,
    val isOtpSent: Boolean = false,
    val isOtpVerified: Boolean = false,
    var isSignedIn: Boolean = false,
)