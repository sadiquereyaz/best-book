package com.nabssam.bestbook.data.remote.dto.auth

data class SignInRequest(
    val username: String,
    val password: String
)

data class SignInResponse(
    val __v: Int,
    val _id: String,
    val createdAt: String,
    val currentClass: String,
    val isAdmin: Boolean,
    val phoneNumber: String,
    val profilePicture: String,
    val subscribedEbook: List<Any>,
    val targetExam: List<String>,
    val targetYear: List<String>,
    val updatedAt: String,
    val username: String
)