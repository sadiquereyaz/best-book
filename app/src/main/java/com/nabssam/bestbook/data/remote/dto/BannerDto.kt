package com.nabssam.bestbook.data.remote.dto

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

data class BannerResponse(
    val banners: List<BannerDto>,
    val success: Boolean
)

data class BannerDto(
    val __v: Int,                  
    val _id: String,
    val cloudinaryId: String,

    @SerializedName("redirectUrl") val redirectLink: String? = null,       // Default null if missing
    val imageUrl: String? = null,
    val isActive: Boolean,

    val createdAt: String,
    val updatedAt: String
)
