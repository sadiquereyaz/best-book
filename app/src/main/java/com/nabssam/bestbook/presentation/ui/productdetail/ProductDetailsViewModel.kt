package com.nabssam.bestbook.presentation.ui.productdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nabssam.bestbook.domain.model.Book
import com.nabssam.bestbook.domain.usecase.product.GetProductDetailsUseCase
import com.nabssam.bestbook.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject  constructor(
    private val getProductDetailsUseCase: GetProductDetailsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<Resource<Book>>(Resource.Loading())
    val uiState: StateFlow<Resource<Book>> = _uiState

    fun fetchProductDetails(productId: String) {
        viewModelScope.launch {
            getProductDetailsUseCase(productId).collect { resource ->
                _uiState.value = resource
            }
        }
    }
}