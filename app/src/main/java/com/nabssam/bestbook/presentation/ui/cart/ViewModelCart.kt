package com.nabssam.bestbook.presentation.ui.cart

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nabssam.bestbook.data.remote.dto.ProductType
import com.nabssam.bestbook.data.repository.UserDataStoreRepoImpl
import com.nabssam.bestbook.domain.repository.CartRepository
import com.nabssam.bestbook.domain.usecase.cart.GetCartItemsUseCase
import com.nabssam.bestbook.domain.usecase.cart.RemoveFromCartUseCase
import com.nabssam.bestbook.domain.usecase.cart.UpdateCartItemQuantityUseCase
import com.nabssam.bestbook.presentation.ui.snackbar.SnackbarManager
import com.nabssam.bestbook.presentation.ui.snackbar.SnackbarMessage
import com.nabssam.bestbook.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "VIEW_MODEL_CART"

@HiltViewModel
class ViewModelCart @Inject constructor(
    private val snackbarManager: SnackbarManager,
    private val updateQuantityUseCase: UpdateCartItemQuantityUseCase,
    private val removeUseCase: RemoveFromCartUseCase,
    private val cartRepository: CartRepository,
    private val getCartItemsUseCase: GetCartItemsUseCase,
    private val userDataStoreRepoImpl: UserDataStoreRepoImpl

) : ViewModel() {

    private val _uiState = MutableStateFlow<CartUiState>(CartUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        fetchAllCartItems()
    }

    fun fetchAllCartItems() {
        try {
            getCartItemsUseCase.invoke().onEach { resource ->
                when (resource) {
                    is Resource.Error -> _uiState.value =
                        CartUiState.Error(resource.message ?: "No items in cart")

                    is Resource.Loading -> _uiState.value = CartUiState.Loading
                    is Resource.Success -> _uiState.value = CartUiState.Idle(
                        allCartItem = resource.data ?: emptyList()
                    )
                }
            }.launchIn(viewModelScope)
        } catch (e: Exception) {
            Log.e(TAG, e.message ?: "Unknown error occurred in Cart ViewModel")
            showSnackBar(message = e.message ?: "Unknown error occurred")
        }
    }

    fun updateQuantity(productId: String, quantity: Int, type: ProductType) {
        viewModelScope.launch {
            try {
                cartRepository.updateQuantity(productId, quantity, type).collect { resource ->
                    when (resource) {
                        is Resource.Error -> {
                            updateCartItemUi(productId=productId, isQuantityUpdating = false, productType = type)
                            showSnackBar(message = resource.message ?: "Error  while modifying cart item")
                        }

                        is Resource.Loading -> {
                            updateCartItemUi(productId = productId, quantity = quantity, productType = type, isQuantityUpdating = true)
                        }

                        is Resource.Success -> {
                            updateCartItemUi(productId=productId, quantity=quantity, isQuantityUpdating = false, productType = type)
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, e.message ?: "Unknown error occurred in Cart ViewModel")
                showSnackBar(message = e.message ?: "Unknown error occurred")
            }
        }
    }

    private fun updateCartItemUi(productId: String, productType: ProductType, quantity: Int? = null, isQuantityUpdating: Boolean = false) {
        try {
            val currentState = _uiState.value
            if (currentState is CartUiState.Idle) {
                val updatedItems = currentState.allCartItem.map { cartItem ->
                    if (cartItem.productId == productId && cartItem.productType == productType) {
                        quantity?.let {
                            cartItem.copy(quantity = quantity,  isModifyingQuantity = isQuantityUpdating)
                        } ?: cartItem.copy(quantity = 0, isModifyingQuantity = false)
                    } else {
                        cartItem
                    }
                }
                _uiState.value = currentState.copy(allCartItem = updatedItems)
            }
        } catch (e: Exception) {
            Log.e(TAG, e.message ?: "Unknown error occurred in Cart ViewModel")
            showSnackBar(message = e.message ?: "Unknown error occurred")
        }
    }

    private fun showSnackBar(message: String){
        viewModelScope.launch {
            try {
                snackbarManager.showSnackbar(SnackbarMessage(message = message))
            } catch (e: Exception) {
                Log.e(TAG, e.message ?: "Unknown error occurred in show snackbar of Cart ViewModel")
            }
        }
    }
}


