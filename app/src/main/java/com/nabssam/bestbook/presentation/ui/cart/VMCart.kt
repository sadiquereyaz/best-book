package com.nabssam.bestbook.presentation.ui.cart

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nabssam.bestbook.data.repository.UserDataStoreRepoImpl
import com.nabssam.bestbook.domain.repository.CartRepository
import com.nabssam.bestbook.domain.usecase.cart.RemoveFromCartUseCase
import com.nabssam.bestbook.domain.usecase.cart.UpdateCartItemQuantityUseCase
import com.nabssam.bestbook.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VMCart @Inject constructor(
    private val updateQuantityUseCase: UpdateCartItemQuantityUseCase,
    private val removeUseCase: RemoveFromCartUseCase,
    private val cartRepository: CartRepository,
    private val userDataStoreRepoImpl: UserDataStoreRepoImpl

) : ViewModel() {

    private val _uiState = MutableStateFlow<CartUiState>(CartUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        fetchAllCartItems()
    }

    fun fetchAllCartItems() {
        viewModelScope.launch {
            _uiState.value = CartUiState.Loading
            cartRepository.fetchCartItems().collect { resource ->
                when (resource) {
                    is Resource.Error -> _uiState.value =
                        CartUiState.Error(resource.message ?: "No items in cart")

                    is Resource.Loading -> _uiState.value = CartUiState.Loading
                    is Resource.Success -> _uiState.value = CartUiState.Idle(
                        allCartItem = resource.data ?: emptyList()
                    )
                }
            }
        }
    }

    fun updateQuantity(productId: String, quantity: Int) {
        val currentState = _uiState.value
        if (currentState is CartUiState.Idle) {
            val updatedItems = currentState.allCartItem.map { cartItem ->
                if (cartItem.productId == productId) {
                    cartItem.copy(quantity = quantity)
                } else {
                    cartItem
                }
            }
            _uiState.value = currentState.copy(allCartItem = updatedItems)
        }

        viewModelScope.launch {
            Log.d("updateQuantity", "updateQuantity: $productId $quantity")
            cartRepository.updateQuantity(productId, quantity).collect {

            }
        }
    }
}


