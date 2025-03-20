package com.nabssam.bestbook.domain.model

data class Review(
    val userId: String?,
    val reviewId: String,
    val username: String,
    val profilePicture: String? = null,
    val date: String,
    val rate: Double,
    val description: String? = null
)