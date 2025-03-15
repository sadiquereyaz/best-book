package com.nabssam.bestbook.data.remote.dto

data class PyqResponse(
    val pyqs: List<PyqDto>,
    val success: Boolean
)

data class PyqDto(
    val __v: Int,                  
    val _id: String,

    val title: String,       // Default null if missing
    val link: String,

    val createdAt: String,
    val updatedAt: String
)
