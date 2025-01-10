package com.nabssam.bestbook.presentation.ui.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nabssam.bestbook.data.remote.dto.CartItemDto
import com.nabssam.bestbook.domain.repository.CartRepository
import com.nabssam.bestbook.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository
) : ViewModel() {

    private val _cartState = MutableStateFlow<Resource<List<CartItemDto>>>(Resource.Loading())
    val cartState = _cartState.asStateFlow()

    fun fetchCartItems(productIds: List<String>) {
        viewModelScope.launch {
            cartRepository.fetchCartItems(productIds).collect { resource ->
                _cartState.value = resource
            }
        }
    }

    fun addProductToCart(userId: String, productId: String) {
        viewModelScope.launch {
            cartRepository.addProductToCart(userId, productId).collect { resource ->
                // Handle response (e.g., update UI)
            }
        }
    }

    fun removeProductFromCart(userId: String, productId: String) {
        viewModelScope.launch {
            cartRepository.removeProductFromCart(userId, productId).collect { resource ->
                // Handle response
            }
        }
    }

    fun clearCart(userId: String) {
        viewModelScope.launch {
            cartRepository.clearCart(userId).collect { resource ->
                // Handle response
            }
        }
    }
}
