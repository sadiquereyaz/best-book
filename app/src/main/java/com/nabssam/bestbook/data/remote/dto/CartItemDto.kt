package com.nabssam.bestbook.data.remote.dto

data class CartItemDto(
    val id: String,             // Unique ID for the cart item
    val productId: String,      // ID of the product
    val productName: String,    // Name of the product
    val productPrice: Double,   // Price of the product
    val quantity: Int,          // Quantity of the product
    val productImage: String    // URL for the product image
)
/*

data class CartItemDto(
    val id: String,             // Product ID
    val name: String,           // Product Name
    val price: Double,          // Product Price
    val imageUrl: String,       // Product Image URL
    val quantity: Int           // Quantity of the product in the cart
)
*/
