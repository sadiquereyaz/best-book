package com.nabssam.bestbook.presentation.ui.book.bookDetail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.nabssam.bestbook.data.mapper.BookMapper
import com.nabssam.bestbook.domain.model.Book
import com.nabssam.bestbook.domain.usecase.book.GetProductDetailsUseCase
import com.nabssam.bestbook.domain.usecase.cart.AddToCartUseCase
import com.nabssam.bestbook.presentation.navigation.Route
import com.nabssam.bestbook.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelBookDetail @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getProductByIdUseCase: GetProductDetailsUseCase,
    private val addToCartUseCase: AddToCartUseCase,
    private val mapper: BookMapper
) : ViewModel() {
    private val id = savedStateHandle.toRoute<Route.BookDetailRoute>().id
    private val _state = MutableStateFlow(StateBookDetail())
    val state = _state.asStateFlow()

    init {
        Log.d("id", id)
        fetchBookDetail()
    }

    fun onEvent(event: EventBookDetail) {
        when (event) {

            is EventBookDetail.Retry -> {
                fetchBookDetail()
            }

            is EventBookDetail.AddToCart -> {
                addToCart(_state.value.fetchedBook)
            }
        }
    }

    private fun fetchBookDetail() {
        viewModelScope.launch {
            getProductByIdUseCase(id).collect { resource ->
                when (resource) {
                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                loading = false,
                                errorMessage = resource.message
                            )
                        }
                    }

                    is Resource.Loading -> {
                        _state.update {
                            it.copy(
                                loading = true
                            )
                        }
                    }

                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                loading = false,
                                fetchedBook = resource.data ?: Book()
                            )
                        }
                    }
                }
            }

        }
    }

    fun addToCart(book: Book) {
        viewModelScope.launch {
            addToCartUseCase(
                mapper.domainToEntity(book)
            )
        }
    }


}
