package com.nabssam.bestbook.domain.repository

import com.nabssam.bestbook.data.remote.dto.CartItemDto
import com.nabssam.bestbook.domain.model.UserOld
import com.nabssam.bestbook.utils.Resource
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    suspend fun fetchCartItems(productIds: List<String>): Flow<Resource<List<CartItemDto>>>
    suspend fun addProductToCart(userId: String, productId: String): Flow<Resource<UserOld>>
    suspend fun removeProductFromCart(userId: String, productId: String): Flow<Resource<UserOld>>
    suspend fun clearCart(userId: String): Flow<Resource<UserOld>>
}
