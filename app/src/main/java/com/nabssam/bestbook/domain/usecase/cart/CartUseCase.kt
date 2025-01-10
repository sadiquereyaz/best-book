package com.nabssam.bestbook.domain.usecase.cart

import com.nabssam.bestbook.data.local.entity.CartItemEntity
import com.nabssam.bestbook.domain.repository.LocalCartRepository
import com.nabssam.bestbook.domain.repository.BookRepository
import com.nabssam.bestbook.utils.ValidationException
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddToCartUseCase @Inject constructor(
    private val localCartRepository: LocalCartRepository,
    private val bookRepository: BookRepository
) {
    suspend operator fun invoke(item: CartItemEntity) {
       // bookRepository.
        //cartRepository.addToCart(item)
    }
}

class RemoveFromCartUseCase @Inject constructor(
    private val repository: LocalCartRepository
) {
    suspend operator fun invoke(productId: String) {
        repository.removeFromCart(productId)
    }
}

class UpdateCartItemQuantityUseCase @Inject constructor(
    private val repository: LocalCartRepository
) {
    suspend operator fun invoke(productId: String, quantity: Int) {
        //validateUpdateQuantity(productId, quantity)
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

class GetAllCartItemsUseCase @Inject constructor(
    private val repository: LocalCartRepository
) {
    operator fun invoke(): Flow<List<CartItemEntity>> {
        return repository.getAllCartItems()
    }
}

class ClearCartUseCase @Inject constructor(
    private val repository: LocalCartRepository
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
