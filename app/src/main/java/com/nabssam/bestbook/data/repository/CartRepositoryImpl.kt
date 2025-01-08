package com.nabssam.bestbook.data.repository

import com.nabssam.bestbook.data.local.dao.CartDao
import com.nabssam.bestbook.data.local.dao.ProductDao
import com.nabssam.bestbook.data.local.entity.CartItemEntity
import com.nabssam.bestbook.data.mapper.BookMapper
import com.nabssam.bestbook.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val cartDao: CartDao,
    private val productDao: ProductDao,
    private val mapper: BookMapper
) : CartRepository {

    override suspend fun addToCart(product: CartItemEntity) {
        val existingCartItem = cartDao.getCartItemById(product.productId)
        if (existingCartItem != null)
            cartDao.insert(existingCartItem.copy(quantity = existingCartItem.quantity + 1)) // Increase quantity
        else
            cartDao.insert(product) // Insert or replace
    }


    override suspend fun removeFromCart(id: String) {
        cartDao.deleteCartItem(id)
    }

    // TODO: If quantity exceeds from available stocks
    override suspend fun updateQuantity(productId: String, quantity: Int) {
        if (quantity <= 0) {
            removeFromCart(productId)
        } else {
            cartDao.updateQuantity(productId, quantity)
        }
    }

    override fun getAllCartItems(): Flow<List<CartItemEntity>> {

        return cartDao.getAllCartItems()
        /*return cartDao.getCartWithProducts().map { cartWithProducts ->
            cartWithProducts.map { cartWithProduct ->
                CartItem(
                    productId = cartWithProduct.cartItem.productId,
                    quantity = cartWithProduct.cartItem.quantity,
                    book = mapper.entityToDomain(cartWithProduct.product)
                )
            }
        }*/
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