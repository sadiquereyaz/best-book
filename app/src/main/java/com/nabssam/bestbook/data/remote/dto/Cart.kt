package com.nabssam.bestbook.data.remote.dto

import androidx.compose.ui.graphics.Color
import com.google.gson.annotations.SerializedName


data class CartResponseFinal(
    val success: Boolean,
    val message: String,
   @SerializedName("cartData") val data: CartData,
)

data class CartData(
    @SerializedName("items") val cartItems: List<CartItemFinal>
)

data class CartItemFinal(
    val product: Product,
    val productType: ProductType,
    val quantity: Int
)

data class Product(
    val _id: String,
    val coverImage: String?,
    val ebookDiscount: Int?,
    val hardcopyDiscount: Int?,
    val stock : Int?,
    val price: Int?,
    val title: String?
)

data class RemoveResponse(
    val __v: Int,
    val _id: String,
    val belongTo: String,
    val coupon: String,
    val createdAt: String,
    val items: List<Item>,
    val subtotal: Int,
    val total: Int,
    val updatedAt: String
)

data class Item(
    val _id: String,
    val productId: String,
    val productType: String,
    val quantity: Int
)
data class RemoveRequest(
    val productId: String,
    val  productType: ProductType
)

enum class ProductType(val color: Color, val type: String) {
    ebook(color = Color(0xFF005205), type = "Ebook"),
    Book(
        color = Color(0xFF5D3700),
        type = "Paperback"
    ),
    Quiz(color = Color(0xFF00178C), type = "Quiz")
}