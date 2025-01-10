package com.nabssam.bestbook.presentation.ui.cart.claude

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModelClaude @Inject constructor(
    private val cartRepositoryClaude: CartRepositoryClaude
) : ViewModel() {
    
    private val _cartState = MutableStateFlow<CartState>(CartState.Loading)
    val cartState: StateFlow<CartState> = _cartState.asStateFlow()
    
    private val _cartOperation = MutableSharedFlow<CartOperation>()
    val cartOperation: SharedFlow<CartOperation> = _cartOperation.asSharedFlow()
    
    fun fetchCartItems(userId: String) {
        viewModelScope.launch {
            _cartState.value = CartState.Loading
            cartRepositoryClaude.getCartItems(userId).fold(
                onSuccess = { cartItems ->
                    _cartState.value = CartState.Success(cartItems)
                },
                onFailure = { error ->
                    _cartState.value = CartState.Error(error.message ?: "Unknown error occurred")
                }
            )
        }
    }
    
    fun addToCart(userId: String, productId: Int, quantity: Int) {
        viewModelScope.launch {
            _cartOperation.emit(CartOperation.Loading)
            cartRepositoryClaude.addToCart(userId, productId, quantity).fold(
                onSuccess = { cartItem ->
                    _cartOperation.emit(CartOperation.Success("Item added to cart"))
                    fetchCartItems(userId)
                },
                onFailure = { error ->
                    _cartOperation.emit(CartOperation.Error(error.message ?: "Failed to add item"))
                }
            )
        }
    }
    
    fun updateCartItem(userId: String, cartItemId: Int, quantity: Int) {
        viewModelScope.launch {
            _cartOperation.emit(CartOperation.Loading)
            cartRepositoryClaude.updateCartItem(cartItemId, quantity).fold(
                onSuccess = { cartItem ->
                    _cartOperation.emit(CartOperation.Success("Cart updated"))
                    fetchCartItems(userId)
                },
                onFailure = { error ->
                    _cartOperation.emit(CartOperation.Error(error.message ?: "Failed to update cart"))
                }
            )
        }
    }
    
    fun removeFromCart(userId: String, cartItemId: Int) {
        viewModelScope.launch {
            _cartOperation.emit(CartOperation.Loading)
            cartRepositoryClaude.removeFromCart(cartItemId).fold(
                onSuccess = {
                    _cartOperation.emit(CartOperation.Success("Item removed from cart"))
                    fetchCartItems(userId)
                },
                onFailure = { error ->
                    _cartOperation.emit(CartOperation.Error(error.message ?: "Failed to remove item"))
                }
            )
        }
    }
    
    fun clearCart(userId: String) {
        viewModelScope.launch {
            _cartOperation.emit(CartOperation.Loading)
            cartRepositoryClaude.clearCart(userId).fold(
                onSuccess = {
                    _cartOperation.emit(CartOperation.Success("Cart cleared"))
                    _cartState.value = CartState.Success(emptyList())
                },
                onFailure = { error ->
                    _cartOperation.emit(CartOperation.Error(error.message ?: "Failed to clear cart"))
                }
            )
        }
    }
}