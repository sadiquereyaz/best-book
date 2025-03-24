package com.nabssam.bestbook.domain.usecase.cart

import com.nabssam.bestbook.utils.ValidationException
import javax.inject.Inject

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