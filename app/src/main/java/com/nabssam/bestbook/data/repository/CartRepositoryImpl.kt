package com.nabssam.bestbook.data.repository

import com.nabssam.bestbook.data.local.dao.CartDao
import com.nabssam.bestbook.data.local.dao.ProductDao
import com.nabssam.bestbook.data.local.entity.CartItemEntity
import com.nabssam.bestbook.data.mapper.ProductMapper
import com.nabssam.bestbook.domain.model.CartItem
import com.nabssam.bestbook.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val cartDao: CartDao,
    private val productDao: ProductDao,
    private val mapper: ProductMapper
) : CartRepository {

    override suspend fun addToCart(productId: String, quantity: Int) {
        // Validate quantity
        if (quantity <= 0) {
            throw IllegalArgumentException("Quantity must be greater than 0")
        }

        // Check if product exists
        val productExists = productDao.exists(productId)
        if (!productExists) {
            throw IllegalArgumentException("Product not found")
        }

        // Check if product is already in cart
        val existingItem = cartDao.getCartItem(productId)
        if (existingItem != null) {
            // Update quantity instead of inserting
            cartDao.safeUpdateQuantity(productId, existingItem.quantity + quantity)
        } else {
            // Insert new cart item
            val cartItem = CartItemEntity(
                productId = productId,
                quantity = quantity
            )
            cartDao.insert(cartItem)
        }
    }

    override suspend fun removeFromCart(productId: String) {
        cartDao.delete(productId)
    }

    override suspend fun updateQuantity(productId: String, quantity: Int) {
        if (quantity <= 0) {
            removeFromCart(productId)
        } else {
            cartDao.safeUpdateQuantity(productId, quantity)
        }
    }

    override fun getCartItems(): Flow<List<CartItem>> {
        return cartDao.getCartWithProducts().map { cartWithProducts ->
            cartWithProducts.map { cartWithProduct ->
                CartItem(
                    productId = cartWithProduct.cartItem.productId,
                    quantity = cartWithProduct.cartItem.quantity,
                    product = mapper.entityToDomain(cartWithProduct.product)
                )
            }
        }
    }

    override fun getTotalItems(): Flow<Int> {
        return cartDao.getTotalItems().map { it ?: 0 }
    }

    override suspend fun clearCart() {
        cartDao.clearCart()
    }

    override suspend fun isProductInCart(productId: String): Boolean {
        return cartDao.isProductInCart(productId)
    }
}