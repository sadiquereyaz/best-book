package com.nabssam.bestbook.presentation.ui.cart.claude

// Data Models
data class CartItemClaude(
    val id: Int,
    val productId: Int,
    val userId: String,
    val quantity: Int,
    val price: Double,
    val productName: String,
    val productImage: String,
    val discount: Int? = null
)

data class AddToCartRequest(
    val userId: String,
    val productId: Int,
    val quantity: Int
)

data class UpdateCartRequest(
    val cartItemId: Int,
    val quantity: Int
)