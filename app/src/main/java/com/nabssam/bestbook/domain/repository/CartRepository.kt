package com.nabssam.bestbook.domain.repository

import com.nabssam.bestbook.data.remote.dto.ProductType
import com.nabssam.bestbook.domain.model.CartItem
import com.nabssam.bestbook.utils.Resource
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    suspend fun fetchCartItems(): Result<List<CartItem>>
    suspend fun addProductToCart(productType: ProductType, productId: String): Result<String?>
    suspend fun remove(productId: String, type: ProductType): Flow<Resource<String?>>
    suspend fun updateQuantity(productId: String, quantity: Int, type: ProductType): Flow<Resource<String>>
    suspend fun clearCart(userId: String): Flow<Resource<Unit>>
}
