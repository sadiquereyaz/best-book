package com.nabssam.bestbook.data.remote.dto.review

import com.google.gson.annotations.SerializedName

data class BookReviewResponse(
    val message: String,
    @SerializedName("reviews") val data: List<ReviewDto>? = null,
    val success: Boolean,
    val statusCode: Int?
)

data class ReviewDto(
    val __v: Int,
    val approved: Boolean,
    val itemId: String,
    val itemType: String,
    val updatedAt: String,

    val _id: String,
    val username: String,
    val userId: String?,
    val profilePicture: String?,
    val description: String?,
    val rating: Double,
    val createdAt: String,
)

