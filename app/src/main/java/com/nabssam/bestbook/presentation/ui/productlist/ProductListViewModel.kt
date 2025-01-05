package com.nabssam.bestbook.presentation.ui.productlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nabssam.bestbook.domain.model.Product
import com.nabssam.bestbook.domain.usecase.cart.AddToCartUseCase
import com.nabssam.bestbook.domain.usecase.product.GetProductsUseCase
import com.nabssam.bestbook.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val addToCartUseCase: AddToCartUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<ProductListState>(ProductListState())
    val state: StateFlow<ProductListState> = _state

    init {
//        loadProducts()
        fetchProducts()
    }

    private fun fetchProducts() {
        viewModelScope.launch {
            getProductsUseCase().collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(isLoading = true)
                    }

                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            products = resource.data ?: emptyList(),
                            isLoading = false,
                            error = null
                        )
                    }

                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            error = resource.message ?: "An unexpected error occurred"
                        )
                    }
                }
            }

        }
    }

   /* private fun loadProducts() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            getProductsUseCase()
                .onEach { products ->
                    _state.value = _state.value.copy(
                        products = products,
                        isLoading = false,
                        error = null
                    )
                }
                .catch { e ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = e.message ?: "An unexpected error occurred"
                    )
                }
                .launchIn(this)
        }
    }*/

    fun addToCart(productId: String, quantity: Int = 1) {
        viewModelScope.launch {
            try {
                addToCartUseCase(productId, quantity)
                // Show success message or handle UI update
            } catch (e: Exception) {
                // Handle error
                _state.value = _state.value.copy(
                    error = e.message ?: "Failed to add item to cart"
                )
            }
        }
    }

    fun retry() {
       // loadProducts()
        fetchProducts()
    }
}

data class ProductListState(
    val products: List<Product> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)