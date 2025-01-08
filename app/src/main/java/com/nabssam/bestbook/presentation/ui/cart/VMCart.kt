package com.nabssam.bestbook.presentation.ui.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nabssam.bestbook.data.local.dao.CartDao
import com.nabssam.bestbook.data.local.entity.CartItemEntity
import com.nabssam.bestbook.domain.usecase.cart.AddToCartUseCase
import com.nabssam.bestbook.domain.usecase.cart.GetAllCartItemsUseCase
import com.nabssam.bestbook.domain.usecase.cart.RemoveFromCartUseCase
import com.nabssam.bestbook.domain.usecase.cart.UpdateCartItemQuantityUseCase
import com.nabssam.bestbook.domain.usecase.product.GetProductDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VMCart @Inject constructor(
    private val addToCartUseCase: AddToCartUseCase,
    private val removeFromCartUseCase: RemoveFromCartUseCase,
    private val getAllCartItemsUseCase: GetAllCartItemsUseCase,
    private val updateQuantityUseCase: UpdateCartItemQuantityUseCase,
    private val getProductDetailsUseCase: GetProductDetailsUseCase,
    private val cartDao:CartDao

) : ViewModel() {

    private val _uiState = MutableStateFlow<CartUiState>(CartUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        fetchAllCartItems()
    }

    fun fetchAllCartItems() {
        viewModelScope.launch {
            //cartDao.createCartItemsTable()
            //cartDao.insertDummy()
            _uiState.value = CartUiState.Loading
            getAllCartItemsUseCase().collect { cartItems ->
                if (cartItems.isEmpty()) {
                    _uiState.value = CartUiState.Error("No items in cart")
                } else {
                   // val deferredBooks = cartItems.map { cartItem ->
                       // async { getProductDetailsUseCase(cartItem.productId).firstOrNull()?.data }
                    }
                // val books = deferredBooks.awaitAll().filterNotNull()
                _uiState.value = CartUiState.Idle(cartItems)
                }
            }
        }

    fun updateQuantity(productId: String, quantity: Int) {
        viewModelScope.launch {
            updateQuantityUseCase(productId, quantity)
        }
    }
}


