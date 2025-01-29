package com.nabssam.bestbook.data.remote.dto

import androidx.compose.ui.graphics.Color
import com.google.gson.annotations.SerializedName


data class CartResponseFinal(
    val cartData: CartData,
    val message: String,
    val success: Boolean
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
    val coverImage: String,
    val ebookDiscount: Int,
    val hardcopyDiscount: Int,
    val price: Int,
    val title: String
)

enum class ProductType(val color: Color, val type: String) {
    ebook(color = Color(0xFF005205), type = "Ebook"),
    Book(
        color = Color(0xFF5D3700),
        type = "Paperback"
    ),
    Quiz(color = Color(0xFF00178C), type = "Quiz")
}