package com.nabssam.bestbook.domain.repository

import com.nabssam.bestbook.domain.model.CartItem
import com.nabssam.bestbook.domain.model.UserOld
import com.nabssam.bestbook.utils.Resource
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    suspend fun fetchCartItems(): Flow<Resource<List<CartItem>>>
    suspend fun addProductToCart(userId: String, productId: String): Flow<Resource<UserOld>>
    suspend fun removeProductFromCart(productId: String): Flow<Resource<Unit>>
    suspend fun updateQuantity(productId: String, quantity: Int): Flow<Resource<String>>
    suspend fun clearCart(userId: String): Flow<Resource<UserOld>>
}
