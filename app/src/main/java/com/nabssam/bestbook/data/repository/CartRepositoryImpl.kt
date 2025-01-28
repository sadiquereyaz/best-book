package com.nabssam.bestbook.data.repository

import android.util.Log
import com.nabssam.bestbook.data.mapper.CartMapper
import com.nabssam.bestbook.data.remote.api.CartApiService
import com.nabssam.bestbook.data.remote.dto.CartAllItems
import com.nabssam.bestbook.domain.model.CartItem
import com.nabssam.bestbook.domain.model.UserOld
import com.nabssam.bestbook.domain.repository.CartRepository
import com.nabssam.bestbook.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val cartApiService: CartApiService,
    private val cartMapper: CartMapper,
) : CartRepository {

    override suspend fun fetchCartItems(): Flow<Resource<List<CartItem>>> = flow {
        emit(Resource.Loading())
        try {
//            val response: Response<CartAllItems> = cartApiService.fetchAll()
            val response = cartApiService.fetchAll()
            Log.d("CART_REPO", "cart response $response")
            if (response.isSuccessful) {
                Log.d("CART_REPO", "response: ${response.body()}")
                response.body()?.items?.let {
                   // emit(Resource.Success(it.map { bookDto -> cartMapper.dtoToDomain(bookDto)}))
                } ?: emit(Resource.Error("No data found"))
            } else {
                emit(Resource.Error(response.message()))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An error occurred"))
        }
    }

    override suspend fun addProductToCart(userId: String, productId: String): Flow<Resource<UserOld>> = flow {
        emit(Resource.Loading())
       /* try {
            val response = cartApiService.update(userId, productId)
            if (response.isSuccessful) {
                emit(Resource.Success(response.body()!!))
            } else {
                emit(Resource.Error(response.message()))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An error occurred"))
        }*/
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
