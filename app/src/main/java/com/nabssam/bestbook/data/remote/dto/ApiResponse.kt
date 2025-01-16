package com.nabssam.bestbook.data.remote.dto

//Generic Api Response data class
data class ApiResponse<T>(
    val statusCode: Int,
    val message: String,
    val success: Boolean,
    val data: T? = null,
    val error: String? = null
)