package com.nabssam.bestbook.domain.model

data class Review(
    val reviewId: String,
    val isOwner: Boolean = false,
    val username: String,
    val profilePicture: String? = null,
    val date: String,
    val rate: Double,
    val description: String? = null
)