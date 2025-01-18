package com.nabssam.bestbook.domain.model

data class CartItem(
    val id: String,
    val productId: String,
    val name: String,
    val price: Int,
    val coverImage: String,
    val stock: Int,
    val disPer: Int,
    val quantity: Int = 0
)
