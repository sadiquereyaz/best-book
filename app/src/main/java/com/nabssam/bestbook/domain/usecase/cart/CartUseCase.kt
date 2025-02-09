package com.nabssam.bestbook.domain.usecase.cart

import android.util.Log
import com.nabssam.bestbook.data.remote.dto.ProductType
import com.nabssam.bestbook.domain.repository.CartRepository
//import com.nabssam.bestbook.domain.repository.LocalCartRepository
import com.nabssam.bestbook.utils.Resource
import com.nabssam.bestbook.utils.ValidationException
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddToCartUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke(id: String, productType: ProductType): Flow<Resource<String?>> {
        Log.d("AddToCartUseCase", "invoke: $id")
        return cartRepository.addProductToCart(
            productId = id, productType = productType
        )
    }
}

class RemoveFromCartUseCase @Inject constructor(
    private val cartRepository: CartRepository

) {
    suspend operator fun invoke(productId: String): Flow<Resource<String?>> {
        return cartRepository.removeProductFromCart(productId)
    }
}

class UpdateCartItemQuantityUseCase @Inject constructor(
) {
    suspend operator fun invoke(productId: String, quantity: Int) {
        //validateUpdateQuantity(productId, quantity)
    }

    private fun validateUpdateQuantity(productId: String, quantity: Int) {
        if (productId.isBlank()) {
            throw ValidationException("Product ID cannot be empty")
        }
        if (quantity <= 0) {
            throw ValidationException("Quantity must be greater than 0")
        }
    }
}

class ClearCartUseCase @Inject constructor(
//    private val repository: LocalCartRepository
) {
    suspend operator fun invoke() {
//        repository.clearCart()
    }
}

/*
class GetCartTotalUseCase @Inject constructor(
    private val repository: CartRepository
) {
    fun invoke(): Flow<Double> {
        return repository.getCartTotal()
    }
}*/
