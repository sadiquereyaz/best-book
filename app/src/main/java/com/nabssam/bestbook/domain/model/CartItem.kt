package com.nabssam.bestbook.domain.model

data class CartItem(
    val productId: String,
    val quantity: Int,
    val product: Product
)
