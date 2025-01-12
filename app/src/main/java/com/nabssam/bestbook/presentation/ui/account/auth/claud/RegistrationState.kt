package com.nabssam.bestbook.presentation.ui.account.auth.claud
import com.nabssam.bestbook.presentation.ui.account.auth.claud.util.RegistrationStep

data class RegistrationState(
    val username: String = "sad",
    val password: String = "111",
    val currentClass: String = "XI-PCM",
    val schoolName: String = "AMU Sr. Sec. School",
    val targetExams: List<String> = emptyList(),
    val targetYear: String = "2026",     // TODO: change to 202
    val mobileNumber: String = "9506198939",
    val otp: String = "",
    val currentStep: RegistrationStep = RegistrationStep.LOGIN,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isOtpSent: Boolean = false,
    val isOtpVerified: Boolean = false
)