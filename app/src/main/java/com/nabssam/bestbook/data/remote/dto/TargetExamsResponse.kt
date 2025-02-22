package com.nabssam.bestbook.data.remote.dto

data class TargetExamsResponse(
    val statusCode: Int,
    val message: String,
    val success: Boolean,
    val data: List<String>
)