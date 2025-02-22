package com.nabssam.bestbook.domain.model.singin

data class SignInResponse(
    val data: Data,
    val message: String,
    val statusCode: Int,
    val success: Boolean
)