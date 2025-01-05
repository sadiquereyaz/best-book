package com.nabssam.bestbook.domain.repository

import com.nabssam.bestbook.domain.model.CartItem
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    suspend fun addToCart(productId: String, quantity: Int)
    suspend fun removeFromCart(productId: String)
    suspend fun updateQuantity(productId: String, quantity: Int)
   // fun getCartItems(): Flow<List<CartItem>>
    suspend fun clearCart()
    fun getCartItems(): Flow<List<CartItem>>
    fun getTotalItems(): Flow<Int>
    suspend fun isProductInCart(productId: String): Boolean
}