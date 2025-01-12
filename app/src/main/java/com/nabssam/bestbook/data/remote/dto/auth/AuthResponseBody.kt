package com.nabssam.bestbook.data.remote.dto.auth

data class AuthResponseBody(
    val statusCode: Int,
    val data: AuthData,
    val message: String,
    val success: Boolean
)