package com.nabssam.bestbook.data.remote.dto.auth

data class SignInRequest(
    val username: String,
    val password: String
)

// SignIn Response
data class UserDto(
    val __v: Int,
    val _id: String,
    val createdAt: String,
    val currentClass: String,
    val isAdmin: Boolean,
    val phoneNumber: String,
    val profilePicture: String,
    val currentToken: String,
    val password: String? = null,   // todo: this should not be returned
    val sessionToken: String,
    val subscribedEbook: List<String>,
    val targetExam: List<String>,
    val targetYear: List<String>,
    val updatedAt: String,
    val username: String
)
