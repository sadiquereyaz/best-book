package com.nabssam.bestbook.data.remote.dto

data class BannerResponse(
    val banners: List<BannerDto>,
    val success: Boolean
)

data class BannerDto(
    val __v: Int,                  
    val _id: String,
    val cloudinaryId: String,

    val redirectLink: String? = null,       // Default null if missing
    val imageUrl: String? = null,
    val isActive: Boolean,

    val createdAt: String,
    val updatedAt: String
)
