package com.nabssam.bestbook.domain.usecase.cart

import android.util.Log
import com.nabssam.bestbook.data.remote.dto.ProductType
import com.nabssam.bestbook.domain.repository.CartRepository
import com.nabssam.bestbook.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AddToCartUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    operator fun invoke(id: String, productType: ProductType): Flow<Resource<String>> = flow {
        emit(Resource.Loading())
        try {
            val result = cartRepository.addProductToCart(productType, id)
            if (result.isSuccess)
                emit(Resource.Success(result.getOrNull()))
            else
                emit(Resource.Error(result.exceptionOrNull()?.message ?: "Unknown error"))
        } catch (e: Exception) {
            Log.e("AddToCartUseCase", "invoke: ${e.message}")
            emit(Resource.Error(e.message ?: "Unknown error"))
        }
    }.flowOn(Dispatchers.IO)
}