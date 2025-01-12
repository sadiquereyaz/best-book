package com.nabssam.bestbook.presentation.ui.account.auth

sealed class EventAuth {
    data class SignIn(val username: String, val password: String) : EventAuth()
    data class Register(
        val username: String,
        val password: String,
        val phone: Int,
        val currentClass: String,
        val targetExamIds: List<String>,
    ) : EventAuth()

    data class ForgetPass(val newPass: String) : EventAuth()
    data class VerifyOtp(val otp: String) : EventAuth()
    data object Retry : EventAuth()
    data object FetchExams : EventAuth()
}

