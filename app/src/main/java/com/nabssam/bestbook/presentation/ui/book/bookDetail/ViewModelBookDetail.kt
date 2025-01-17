package com.nabssam.bestbook.presentation.ui.book.bookDetail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.nabssam.bestbook.data.mapper.BookMapper
import com.nabssam.bestbook.domain.model.Book
import com.nabssam.bestbook.domain.usecase.book.GetBookByIdUC
import com.nabssam.bestbook.domain.usecase.book.GetBooksByExamUC
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
    private val getBookByIdUC: GetBookByIdUC,
    private val addToCartUseCase: AddToCartUseCase,
    private val getBooksByExamUseCase: GetBooksByExamUC,
    private val mapper: BookMapper
) : ViewModel() {
    private val id = savedStateHandle.toRoute<Route.BookDetailRoute>().id
    private val _state = MutableStateFlow(StateBookDetail())
    val state = _state.asStateFlow()

    init {
        fetchBookDetail()
        fetchRelatedBooks()
    }

    private fun purchaseNow() {
        TODO("Not yet implemented")
    }

    fun onEvent(event: EventBookDetail) {
        when (event) {
            is EventBookDetail.Retry -> {
                fetchBookDetail()
                fetchRelatedBooks()
            }

            is EventBookDetail.AddToCart -> {
                addToCart(id)
            }

            is EventBookDetail.BookTypeSelect -> {
                _state.update {
                    it.copy(buttonState = event.btnState)
                }
            }

            is EventBookDetail.ButtonClick -> {
                when (_state.value.buttonState) {
                    ButtonType.ADD_TO_CART -> {
                        addToCart(id)
                        _state.update { it.copy(buttonState = ButtonType.GO_TO_CART) }
                    }
//                    ButtonType.GO_TO_CART -> TODO()
                    else -> {}
                }
            }
        }
    }

    private fun fetchBookDetail() {
        viewModelScope.launch {
            getBookByIdUC(productId = /*id*/  "book1").collect { resource ->        //todo: remove default arg
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

    private fun fetchRelatedBooks() {
        viewModelScope.launch {
            getBooksByExamUseCase(/*targetExam = state.value.fetchedBook.exam*/"JEE Main").collect { resource ->   // todo: uncomment
                when (resource) {
                    is Resource.Loading -> {
                        _state.update { it.copy(isListFetching = true) }
                    }

                    is Resource.Success -> {
                        Log.d("BOOK_DETAIL_VM", "fetchBooks: ${resource.data}")
                        _state.update {
                            it.copy(
                                fetchedList = resource.data ?: emptyList(),
                                isListFetching = false
                            )
                        }
                    }

                    is Resource.Error -> {
                        _state.update {
                            it.copy(isListFetching = false, listError = resource.message)
                        }
                    }
                }
            }
        }
    }

    fun addToCart(bookId: String) {
        viewModelScope.launch {
            /*  addToCartUseCase(
                  mapper.domainToEntity(book)
              )*/
        }
    }


}
