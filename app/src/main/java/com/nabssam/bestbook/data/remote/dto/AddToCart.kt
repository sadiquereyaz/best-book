package com.nabssam.bestbook.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.nabssam.bestbook.data.remote.dto.auth.UserDto

data class UpdateQuantityRequest(
    @SerializedName("productId") val bookId: String,
    @SerializedName("quantity") val quantity: Int,
    @SerializedName("productType") val type: ProductType
)

data class AddToCartRequest(
    @SerializedName("productId") val bookId: String,
    @SerializedName("quantity") val quantity: Int,
    @SerializedName("bookType") val productType: ProductType
)

data class CartResponse(
    val __v: Int,
    val _id: String,
    val belongTo: UserDto,
    val coupon: String? = null,
    val createdAt: String,
    @SerializedName("items") val cartItemDto: List<CartItemDTO>? = null,
    val subtotal: Int,
    val total: Int,
    val updatedAt: String
)

data class CartItemDtoFree(
    val _id: String,
    @SerializedName("productId") val bookDto: CartItemDTO,
    val productType: String,
    val quantity: Int
)
data class CartItemDTO(
    val __v: Int,
    val _id: String,
    val coverImage: String,
    val description: String,
    val eBook: String,
    val ebookDiscount: Int,
    val hardcopyDiscount: Int,
    val images: List<Any>,
    val isEbookAvailable: Boolean,
    val price: Int,
    val rating: Int,
    val reviews: List<Any>,
    val reviewsId: List<Any>,
    val stock: Int,
    val targetExam: String,
    val title: String
)