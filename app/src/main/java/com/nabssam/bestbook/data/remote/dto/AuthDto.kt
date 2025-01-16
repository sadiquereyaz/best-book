package com.nabssam.bestbook.data.remote.dto

import com.nabssam.bestbook.domain.model.User

data class SignInResponseBody(
    val statusCode: Int,
    val data: ResponseData,
    val message: String,
    val success: Boolean
)

data class Avatar(
    val url: String,
    val localPath: String,
    val _id: String
)

data class SignInRequest(
    val email: String,
    val password: String
)


data class ResponseData(
    val user: User,
    val accessToken: String,
    val refreshToken: String
)

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
