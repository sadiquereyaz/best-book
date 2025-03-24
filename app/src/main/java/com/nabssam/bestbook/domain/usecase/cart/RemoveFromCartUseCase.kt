package com.nabssam.bestbook.domain.usecase.cart

import com.nabssam.bestbook.data.remote.dto.ProductType
import com.nabssam.bestbook.domain.repository.CartRepository
import com.nabssam.bestbook.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RemoveFromCartUseCase @Inject constructor(
    private val cartRepository: CartRepository

) {
    suspend operator fun invoke(productId: String,productType: ProductType): Flow<Resource<String?>> {
        return cartRepository.remove(productId, productType)
    }
}