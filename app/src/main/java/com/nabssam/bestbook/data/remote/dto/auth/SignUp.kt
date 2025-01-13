package com.nabssam.bestbook.data.remote.dto.auth

data class SignUpRequest(
    val currentClass: String,
    val password: String,
    val phoneNumber: String,
    val targetExam: String,
    val targetYear: Int,
    val username: String
)

data class SignUpResponse(
    val success: Boolean,
    val message: String
)