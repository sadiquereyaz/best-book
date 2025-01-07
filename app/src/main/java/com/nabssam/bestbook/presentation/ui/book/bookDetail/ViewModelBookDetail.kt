package com.nabssam.bestbook.presentation.ui.book.bookDetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.nabssam.bestbook.domain.model.Book
import com.nabssam.bestbook.domain.repository.CartRepository
import com.nabssam.bestbook.utils.Resource
import com.nabssam.bestbook.domain.repository.ProductRepository
import com.nabssam.bestbook.domain.usecase.cart.AddToCartUseCase
import com.nabssam.bestbook.domain.usecase.product.GetProductDetailsUseCase
import com.nabssam.bestbook.presentation.navigation.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelBookDetail @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getProductByIdUseCase: GetProductDetailsUseCase,
    private val addToCartUseCase: AddToCartUseCase
) : ViewModel() {
    private val id = savedStateHandle.toRoute<Route.ProductDetailRoute>().id
    private val _state = MutableStateFlow<Resource<Book>>(Resource.Loading())
    val state = _state.asStateFlow()

    init {
        fetchBookDetail()
    }

     fun fetchBookDetail() {
        viewModelScope.launch {
            getProductByIdUseCase(id)
                .catch { e ->
                    _state.value = Resource.Error(e.message ?: "An unexpected error occurred!")
                }
                .collect { resource ->
                    _state.value = resource
                }
        }
    }

    fun addToCart() {
        viewModelScope.launch {
            addToCartUseCase(productId = id)
        }
    }

    /*
    private fun fetchBookDetail1() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            getProductByIdUseCase(id)
                .catch { e ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        errorBook = e.message ?: "An unexpected error occurred"
                    )
                }
                .collect { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            _state.value = _state.value.copy(
                                isLoading = false,
                                fetchedBook = resource.data,
                                errorBook = null
                            )
                        }

                        is Resource.Error -> {
                            _state.value = _state.value.copy(
                                isLoading = false,
                                errorBook = resource.message // Now this matches the String? type
                            )
                        }

                        is Resource.Loading -> {
                            _state.value = _state.value.copy(isLoading = true)
                        }
                    }
                }
        }
    }*/
}
