package com.nabssam.bestbook.presentation.ui.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nabssam.bestbook.domain.model.CartItem
import com.nabssam.bestbook.domain.usecase.cart.AddToCartUseCase
import com.nabssam.bestbook.domain.usecase.cart.GetCartItemsUseCase
import com.nabssam.bestbook.domain.usecase.cart.RemoveFromCartUseCase
import com.nabssam.bestbook.domain.usecase.product.GetProductDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VMCart @Inject constructor(
    private val addToCartUseCase: AddToCartUseCase,
    private val removeFromCartUseCase: RemoveFromCartUseCase,
    private val getCartItemsUseCase: GetCartItemsUseCase,
    private val getProductDetailsUseCase: GetProductDetailsUseCase

) : ViewModel() {

    private val _uiState = MutableStateFlow<CartUiState>(CartUiState.Loading)
    val uiState = _uiState.asStateFlow()

//    var uiState by mutableStateOf<CartUiState>(CartUiState.Loading)
//        private set

    init {
        fetchAllCartItems()
    }

    fun fetchAllCartItems() {
        viewModelScope.launch {
            getCartItemsUseCase().collect { cartItems ->
                if (cartItems.isEmpty()) {
                    _uiState.value = CartUiState.Error("No items in cart")
                } else {
                    val deferredBooks = cartItems.map { cartItem ->
                       // async { getProductDetailsUseCase(cartItem.productId).firstOrNull()?.data }
                    }
                   // val books = deferredBooks.awaitAll().filterNotNull()
                    _uiState.value = CartUiState.Idle(cartItems)
                }
            }
        }
    }

    fun addToCart(productId: String, quantity: Int = 1) {
        viewModelScope.launch {
            addToCartUseCase(productId, quantity)
        }
    }

    fun removeFromCart(productId: String) {
        viewModelScope.launch {
            removeFromCartUseCase(productId)
        }
    }
}

