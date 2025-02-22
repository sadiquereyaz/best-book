package com.nabssam.bestbook.data.remote.dto.product

import com.google.gson.annotations.SerializedName

data class BookDtoFreeApi(
    @SerializedName("id") val id: String,
    @SerializedName("title") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("price") val price: Double,
    @SerializedName("image") val image: String,
    @SerializedName("category") val category: String,
    @SerializedName("rating") val rating: Rate,
)

data class Rate(
    @SerializedName("rate") val rate: Double,
    @SerializedName("count") val count: Int
)
