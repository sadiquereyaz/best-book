package com.nabssam.bestbook.data.repository

import com.nabssam.bestbook.data.remote.api.CartApiService
import com.nabssam.bestbook.data.remote.dto.CartItemDto
import com.nabssam.bestbook.domain.model.UserOld
import com.nabssam.bestbook.domain.repository.CartRepository
import com.nabssam.bestbook.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val cartApiService: CartApiService
) : CartRepository {

    override suspend fun fetchCartItems(productIds: List<String>): Flow<Resource<List<CartItemDto>>> = flow {
        emit(Resource.Loading())
        try {
            /*val response = cartApiService.fetchCartItems(productIds)
            if (response.isSuccessful) {
                emit(Resource.Success(response.body() ?: emptyList()))
            } else {
                emit(Resource.Error(response.message()))
            }*/
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An error occurred"))
        }
    }

    override suspend fun addProductToCart(userId: String, productId: String): Flow<Resource<UserOld>> = flow {
        emit(Resource.Loading())
        try {
            val response = cartApiService.addProductToCart(userId, productId)
            if (response.isSuccessful) {
                emit(Resource.Success(response.body()!!))
            } else {
                emit(Resource.Error(response.message()))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An error occurred"))
        }
    }

    override suspend fun removeProductFromCart(userId: String, productId: String): Flow<Resource<UserOld>> = flow {
        emit(Resource.Loading())
        try {
            val response = cartApiService.removeProductFromCart(userId, productId)
            if (response.isSuccessful) {
                emit(Resource.Success(response.body()!!))
            } else {
                emit(Resource.Error(response.message()))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An error occurred"))
        }
    }

    override suspend fun clearCart(userId: String): Flow<Resource<UserOld>> = flow {
        emit(Resource.Loading())
        try {
            val response = cartApiService.clearCart(userId)
            if (response.isSuccessful) {
                emit(Resource.Success(response.body()!!))
            } else {
                emit(Resource.Error(response.message()))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An error occurred"))
        }
    }
}
