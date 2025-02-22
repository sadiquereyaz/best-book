package com.nabssam.bestbook.data.remote.dto

data class CartItemU(
    val cartItemId: String,             // Unique ID for the cart item
    val bookId: String,
    val name: String,
    val price: Int,
    val disPer: Int,
    val stock: Int,
    val quantity: Int,
    val coverImage: String,
)