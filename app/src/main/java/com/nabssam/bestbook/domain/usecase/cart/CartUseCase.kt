package com.nabssam.bestbook.domain.usecase.cart

import com.nabssam.bestbook.domain.model.CartItem
import com.nabssam.bestbook.domain.repository.CartRepository
import com.nabssam.bestbook.domain.repository.ProductRepository
import com.nabssam.bestbook.utils.ValidationException
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddToCartUseCase @Inject constructor(
    private val cartRepository: CartRepository,
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke(productId: String, quantity: Int) {
        validateAddToCart(productId, quantity)
        val product = productRepository.getProductById(productId) 
            ?: throw ValidationException("Product not found")
        cartRepository.addToCart(productId, quantity)
    }

    private fun validateAddToCart(productId: String, quantity: Int) {
        if (productId.isBlank()) {
            throw ValidationException("Product ID cannot be empty")
        }
        if (quantity <= 0) {
            throw ValidationException("Quantity must be greater than 0")
        }
    }
}

class RemoveFromCartUseCase @Inject constructor(
    private val repository: CartRepository
) {
    suspend operator fun invoke(productId: String) {
        if (productId.isBlank()) {
            throw ValidationException("Product ID cannot be empty")
        }
        repository.removeFromCart(productId)
    }
}

class UpdateCartItemQuantityUseCase @Inject constructor(
    private val repository: CartRepository
) {
    suspend operator fun invoke(productId: String, quantity: Int) {
        validateUpdateQuantity(productId, quantity)
        repository.updateQuantity(productId, quantity)
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

class GetCartItemsUseCase @Inject constructor(
    private val repository: CartRepository
) {
    operator fun invoke(): Flow<List<CartItem>> {
        return repository.getCartItems()
    }
}

class ClearCartUseCase @Inject constructor(
    private val repository: CartRepository
) {
    suspend operator fun invoke() {
        repository.clearCart()
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
