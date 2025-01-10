package com.nabssam.bestbook.domain.repository

import com.nabssam.bestbook.data.local.entity.CartItemEntity
import kotlinx.coroutines.flow.Flow

interface LocalCartRepository {
    suspend fun addToCart(product: CartItemEntity)
    suspend fun removeFromCart(id: String)
    suspend fun updateQuantity(productId: String, quantity: Int)
   // fun getCartItems(): Flow<List<CartItem>>
    suspend fun clearCart()
    fun getAllCartItems(): Flow<List<CartItemEntity>>
    fun getTotalItems(): Flow<Int>
    suspend fun isProductInCart(productId: String): Boolean
}