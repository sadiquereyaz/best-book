package com.nabssam.bestbook.domain.model

data class SignInResponse(
    val token: String,
    val user: User
)

data class RegisterRequest(
    val username: String,
    val email: String,
    val password: String,
    val role: String = "USER",
)


data class Avatar(
    val url: String,
    val localPath: String,
    val _id: String
)

data class AuthResponse(
    val statusCode: Int,
    val data: AuthData,
    val message: String,
    val success: Boolean
)

data class AuthData(
    val user: User,
    val accessToken: String,
    val refreshToken: String
)

data class SignInRequest(
    val email: String,
    val password: String
)

