package com.nabssam.bestbook.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ProductDto(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("price") val price: Double,
    @SerializedName("imageUrl") val imageUrl: String,
    @SerializedName("category") val category: String,
    @SerializedName("rating") val rating: Float,
    @SerializedName("stock") val stock: Int
)