package com.nabssam.bestbook.data.remote.dto

data class CategoryResponse(
    val categories: List<String>,
    val message: String,
    val status: String
)