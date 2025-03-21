package com.nabssam.bestbook.data.remote.dto.review

data class ReviewRequest(
    val username: String,
    val description: String,
    val rating: Int,
    val itemType: String,
    val itemId: String
)