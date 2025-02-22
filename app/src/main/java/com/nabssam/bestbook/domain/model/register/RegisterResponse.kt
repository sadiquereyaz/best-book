package com.nabssam.bestbook.domain.model.register

data class RegisterResponse(
    val data: Data,
    val message: String,
    val statusCode: Int,
    val success: Boolean
)