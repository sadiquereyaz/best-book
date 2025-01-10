package com.nabssam.bestbook.domain.model

data class SignInRequest(
    val email: String,
    val password: String
)

data class SignInResponse(
    val token: String,
    val user: User
)

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String,
    val phone: String?
)