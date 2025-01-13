package com.nabssam.bestbook.presentation.ui.cart.claude

import android.util.Log
import com.nabssam.bestbook.data.repository.UserPreferencesRepository
import javax.inject.Inject

class CartRepositoryClaude @Inject constructor(
    private val cartApiServiceClaude: CartApiServiceClaude,
    private val userPreferencesRepository: UserPreferencesRepository
) {
    /*val id:String = userPreferencesRepository.user.collect{
        it?._id ?: "NO ID FOUND!"
    }*/
    suspend fun getCartItems(userId: String): Result<List<CartItemClaude>> {
        return try {
            val response = cartApiServiceClaude.getCartItems("67839da16e927a9ab6a3f107")
            Log.d("CART_RESPONSE", "${response.body()}")

            if (response.isSuccessful) {
                Result.success(response.body() ?: emptyList())
            } else {
                Result.failure(Exception("Failed to fetch cart items"))
            }

            //Result.success(CartItemObj.cartItemClaude)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun addToCart(userId: String, productId: Int, quantity: Int): Result<CartItemClaude> {
        return try {
            val request = AddToCartRequest(userId, productId, quantity)
            val response = cartApiServiceClaude.addToCart(request)
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to add item to cart"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun updateCartItem(cartItemId: Int, quantity: Int): Result<CartItemClaude> {
        return try {
            val request = UpdateCartRequest(cartItemId, quantity)
            val response = cartApiServiceClaude.updateCartItem(cartItemId, request)
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to update cart item"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun removeFromCart(cartItemId: Int): Result<Unit> {
        return try {
            val response = cartApiServiceClaude.removeFromCart(cartItemId)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Failed to remove item from cart"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun clearCart(userId: String): Result<Unit> {
        return try {
            val response = cartApiServiceClaude.clearCart(userId)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Failed to clear cart"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}