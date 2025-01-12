package com.nabssam.bestbook.data.remote.dto.auth

data class SignUpRequest(
    val email: String,
    val password: String,
    val role: String = "USER",
    val currentClass: String,
    val schoolName: String,
    val targetExams: List<String>,
    val targetYear: String,
    val mobileNumber: String,
    val username: String
)