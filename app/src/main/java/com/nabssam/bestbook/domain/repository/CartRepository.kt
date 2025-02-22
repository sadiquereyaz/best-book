package com.nabssam.bestbook.domain.repository

import com.nabssam.bestbook.data.remote.dto.ProductType
import com.nabssam.bestbook.domain.model.CartItem
import com.nabssam.bestbook.utils.Resource
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    suspend fun fetchCartItems(): Flow<Resource<List<CartItem>>>
    suspend fun addProductToCart(productType: ProductType, productId: String): Flow<Resource<String?>>
    suspend fun removeProductFromCart(productId: String): Flow<Resource<String?>>
    suspend fun updateQuantity(productId: String, quantity: Int): Flow<Resource<String>>
    suspend fun clearCart(userId: String): Flow<Resource<Unit>>
}
