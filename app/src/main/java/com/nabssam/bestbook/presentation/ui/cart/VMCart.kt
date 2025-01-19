package com.nabssam.bestbook.presentation.ui.cart

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nabssam.bestbook.data.local.dao.CartDao
import com.nabssam.bestbook.data.repository.UserPrefRepoImpl
import com.nabssam.bestbook.domain.repository.CartRepository
import com.nabssam.bestbook.domain.usecase.book.GetBookByIdUC
import com.nabssam.bestbook.domain.usecase.cart.AddToCartUseCase
import com.nabssam.bestbook.domain.usecase.cart.GetAllCartItemsUseCase
import com.nabssam.bestbook.domain.usecase.cart.RemoveFromCartUseCase
import com.nabssam.bestbook.domain.usecase.cart.UpdateCartItemQuantityUseCase
import com.nabssam.bestbook.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VMCart @Inject constructor(
    private val updateQuantityUseCase: UpdateCartItemQuantityUseCase,
    private val cartRepository: CartRepository,
    private val userPrefRepoImpl: UserPrefRepoImpl

) : ViewModel() {

    private val _uiState = MutableStateFlow<CartUiState>(CartUiState.Loading)
    val uiState = _uiState.asStateFlow()
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

        fetchAllCartItems()
    }

    fun fetchAllCartItems() {
        viewModelScope.launch {
            _uiState.value = CartUiState.Loading
            cartRepository.fetchCartItems(userId).collect { resource ->
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
    }
}


