package com.nabssam.bestbook.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CartAllItems(
    val __v: Int,
    val _id: String,
    val belongTo: BelongTo,
    val coupon: String,
    val createdAt: String,
    val items: List<CartItemMain>? =null,
    val subtotal: Int,
    val total: Int,
    val updatedAt: String
)


data class BelongTo(
    val __v: Int,
    val _id: String,
    val createdAt: String,
    val currentClass: String,
    val currentToken: String,
    val isAdmin: Boolean,
    val password: String,
    val phoneNumber: String,
    val profilePicture: String,
    val sessionToken: String,
    val subscribedEbook: List<String>,
    val targetExam: List<String>,
    val targetYear: List<String>,
    val updatedAt: String,
    val username: String
)

data class CartItemMain(
    val _id: String,
    @SerializedName("productId") val productMain: ProductMain,
    val productType: String,
    val quantity: Int=0
)

data class ProductMain(
    val __v: Int,
    val _id: String,
    val coverImage: String,
    val description: String,
    val eBook: String,
    val ebookDiscount: Int,
    val hardcopyDiscount: Int,
    val images: List<String>,
    val isEbookAvailable: Boolean,
    val price: Int,
    val rating: Int,
    val reviews: List<Review>,
    val reviewsId: List<String>,
    val stock: Int,
    val targetExam: String,
    val title: String
)