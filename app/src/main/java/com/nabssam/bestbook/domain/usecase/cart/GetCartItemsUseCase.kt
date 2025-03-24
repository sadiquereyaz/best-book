package com.nabssam.bestbook.domain.usecase.cart

import com.nabssam.bestbook.domain.model.CartItem
import com.nabssam.bestbook.domain.repository.CartRepository
import com.nabssam.bestbook.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCartItemsUseCase @Inject constructor(
    private val repository: CartRepository
) {
    fun invoke(): Flow<Resource<List<CartItem>>> = flow {
        emit(Resource.Loading())
        try {
            val result = repository.fetchCartItems()
            if (result.isSuccess) emit(Resource.Success(result.getOrNull())) else
                emit(Resource.Error(result.exceptionOrNull()?.message ?: "Unknown error"))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unknown error"))
        }
    }
}
