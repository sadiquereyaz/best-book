package com.nabssam.bestbook.presentation.ui.cart.claude

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nabssam.bestbook.data.repository.UserDataStoreRepoImpl
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
    private val cartRepositoryClaude: CartRepositoryClaude,
    private val userDataStoreRepoImpl: UserDataStoreRepoImpl
) : ViewModel() {

    var userId: String = ""

    init {
        viewModelScope.launch {
            userDataStoreRepoImpl.user.collect {
                userId = it?.username ?: "NO ID FOUND!"
            }
        }
            Log.d("USER_ID", userId)
    }

    private val _cartState = MutableStateFlow<CartState>(CartState.Loading)
    val cartState: StateFlow<CartState> = _cartState.asStateFlow()

    private val _cartOperation = MutableSharedFlow<CartOperation>()
    val cartOperation: SharedFlow<CartOperation> = _cartOperation.asSharedFlow()

    fun fetchCartItems() {
        viewModelScope.launch {
            _cartState.value = CartState.Loading
            /*userPreferencesRepository.user.collect {
                userId = it?._id ?: "NO ID FOUND!"
            }*/
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

    fun addToCart(productId: Int, quantity: Int) {
        viewModelScope.launch {
            _cartOperation.emit(CartOperation.Loading)
            cartRepositoryClaude.addToCart(userId, productId, quantity).fold(
                onSuccess = { cartItem ->
                    _cartOperation.emit(CartOperation.Success("Item added to cart"))
                    fetchCartItems()
                },
                onFailure = { error ->
                    _cartOperation.emit(CartOperation.Error(error.message ?: "Failed to add item"))
                }
            )
        }
    }

    fun updateCartItem(cartItemId: Int, quantity: Int) {
        viewModelScope.launch {
            _cartOperation.emit(CartOperation.Loading)
            cartRepositoryClaude.updateCartItem(cartItemId, quantity).fold(
                onSuccess = { cartItem ->
                    _cartOperation.emit(CartOperation.Success("Cart updated"))
                    fetchCartItems()
                },
                onFailure = { error ->
                    _cartOperation.emit(
                        CartOperation.Error(
                            error.message ?: "Failed to update cart"
                        )
                    )
                }
            )
        }
    }

    fun removeFromCart(cartItemId: Int) {
        viewModelScope.launch {
            _cartOperation.emit(CartOperation.Loading)
            cartRepositoryClaude.removeFromCart(cartItemId).fold(
                onSuccess = {
                    _cartOperation.emit(CartOperation.Success("Item removed from cart"))
                    fetchCartItems()
                },
                onFailure = { error ->
                    _cartOperation.emit(
                        CartOperation.Error(
                            error.message ?: "Failed to remove item"
                        )
                    )
                }
            )
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            _cartOperation.emit(CartOperation.Loading)
            cartRepositoryClaude.clearCart(userId).fold(
                onSuccess = {
                    _cartOperation.emit(CartOperation.Success("Cart cleared"))
                    _cartState.value = CartState.Success(emptyList())
                },
                onFailure = { error ->
                    _cartOperation.emit(
                        CartOperation.Error(
                            error.message ?: "Failed to clear cart"
                        )
                    )
                }
            )
        }
    }
}