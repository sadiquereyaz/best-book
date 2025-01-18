package com.nabssam.bestbook.presentation.ui.cart

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nabssam.bestbook.data.remote.dto.CartItemU
import com.nabssam.bestbook.data.repository.UserPrefRepoImpl
import com.nabssam.bestbook.domain.model.CartItem
import com.nabssam.bestbook.domain.repository.CartRepository
import com.nabssam.bestbook.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository,
    private val userPrefRepoImpl: UserPrefRepoImpl
) : ViewModel() {

    private val _cartState = MutableStateFlow<Resource<List<CartItem>>>(Resource.Loading())
    val cartState = _cartState.asStateFlow()
    var userId: String = ""

    init {
        Log.d("CART_VM", "user id: $userId")
        viewModelScope.launch {
            userPrefRepoImpl.user.collect {
                userId = it?.id ?: "NO ID FOUND!"
            }
        }
        Log.d("CART_VM", "user id: $userId")
        //fetchCartItems()
    }

    private fun fetchCartItems() {
        viewModelScope.launch {
            cartRepository.fetchCartItems(userId).collect { resource ->
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
