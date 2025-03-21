package com.nabssam.bestbook.presentation.ui.book.bookDetail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.nabssam.bestbook.data.remote.dto.ProductType
import com.nabssam.bestbook.data.remote.dto.review.ReviewRequest
import com.nabssam.bestbook.domain.model.Book
import com.nabssam.bestbook.domain.usecase.book.GetBookByIdUC
import com.nabssam.bestbook.domain.usecase.book.GetBooksByExamUC
import com.nabssam.bestbook.domain.usecase.cart.AddToCartUseCase
import com.nabssam.bestbook.domain.usecase.review.AddReviewUseCase
import com.nabssam.bestbook.domain.usecase.review.DeleteReviewUseCase
import com.nabssam.bestbook.domain.usecase.review.GetBookReviewsUseCase
import com.nabssam.bestbook.presentation.navigation.Route
import com.nabssam.bestbook.presentation.theme.onErrorDark
import com.nabssam.bestbook.presentation.ui.snackbar.SnackbarManager
import com.nabssam.bestbook.presentation.ui.snackbar.SnackbarMessage
import com.nabssam.bestbook.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "VM_BOOK_DETAIL"

@HiltViewModel
class ViewModelBookDetail @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val snackbarManager: SnackbarManager,
    private val getBookByIdUC: GetBookByIdUC,
    private val addToCartUseCase: AddToCartUseCase,
    private val getBooksByExamUseCase: GetBooksByExamUC,
    private val getBookReviewsUseCase: GetBookReviewsUseCase,
    private val addReviewUseCase: AddReviewUseCase,
    private val deleteReviewUseCase: DeleteReviewUseCase
) : ViewModel() {

    private val id = savedStateHandle.toRoute<Route.BookDetailRoute>().id

    private val _uiState = MutableStateFlow(StateBookDetail())
    val uiState = _uiState.asStateFlow()

    init {
//        fetchBookDetail()
        onEvent(EventBookDetail.Initialize)
    }

    fun onEvent(event: EventBookDetail) {
        when (event) {
            is EventBookDetail.Initialize -> {
                fetchBookDetail()
                fetchReview()
            }

            is EventBookDetail.AddToCart -> {
                addToCart()
            }

            /*is EventBookDetail.BookTypeSelect -> {
                _state.update {
                    it.copy(buttonState = event.btnState)
                }
            }*/

            is EventBookDetail.ProductTypeSelect -> {
                _uiState.update {
                    it.copy(productType = event.type)
                }
            }

            is EventBookDetail.ButtonClick -> {
                when (uiState.value.buttonState) {
                    ButtonType.ADD_TO_CART -> {
                        _uiState.update { it.copy(buttonState = ButtonType.GO_TO_CART) }
                        addToCart()
                    }
//                    ButtonType.GO_TO_CART -> TODO()
                    else -> {}
                }
            }

            is EventBookDetail.UpdateRateReview -> updateRateReviewField(event.rate, event.review)
            is EventBookDetail.FetchReviews -> fetchReview()
            is EventBookDetail.DeleteReview -> {
                deleteReview(event.reviewId)
            }
            is EventBookDetail.SubmitReview -> submitReview()
        }
    }

    private fun fetchBookDetail() {
        viewModelScope.launch {
            getBookByIdUC(productId = id).collect { resource ->
                when (resource) {
                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(
                                loading = false,
                                errorMessage = resource.message
                            )
                        }
                    }

                    is Resource.Loading -> {
                        _uiState.update {
                            it.copy(
                                loading = true
                            )
                        }
                    }

                    is Resource.Success -> {
                         Log.d(TAG, "fetchBookDetail: ${resource.data}")
                        _uiState.update {
                            it.copy(
                                loading = false,
                                fetchedBook = resource.data ?: Book()
                            )
                        }
                        fetchRelatedBooks()
                    }
                }
            }
        }
    }

    private fun fetchRelatedBooks() {
        viewModelScope.launch {
            uiState.value.fetchedBook.exam?.let { target ->
                getBooksByExamUseCase(targetExam = target).collect { resource ->
                    when (resource) {
                        is Resource.Loading -> {
                            _uiState.update { it.copy(isListFetching = true) }
                        }

                        is Resource.Success -> {
//                            Log.d("BOOK_DETAIL_VM", "Related books: ${resource.data}")
                            _uiState.update {
                                it.copy(
                                    fetchedList = resource.data?.filter { it.name != uiState.value.fetchedBook.name }
                                        ?: emptyList(),
                                    isListFetching = false
                                )
                            }
                        }

                        is Resource.Error -> {
                            _uiState.update {
                                it.copy(isListFetching = false, listError = resource.message)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun updateRateReviewField(rate: Int, review: String) {
        _uiState.update {
            it.copy(rate = rate, review = review)
        }
    }

    private fun fetchReview() =
        getBookReviewsUseCase.invoke(id, true).onEach { resource ->
            when (resource) {
                is Resource.Error -> {
                    _uiState.update {
                        it.copy(reviewError = it.errorMessage, reviewLoading = false)
                    }
                    showSnackbar(
                        message = resource.message ?: "Unknown Error",
                        action = { onEvent(EventBookDetail.FetchReviews) },
                        label = "Retry"
                    )
                }

                is Resource.Loading -> _uiState.update {
                    it.copy(
                        reviewLoading = true,
                        reviewError = null
                    )
                }

                is Resource.Success -> _uiState.update {
                    //Log.d(TAG, "fetchReview: ${resource.data}")

                    it.copy(
                        reviewsList = resource.data ?: emptyList(),
                        reviewLoading = false,
                        reviewError = null
                    )
                }
            }
        }.launchIn(viewModelScope)

    private fun submitReview() = addReviewUseCase.invoke(
        ReviewRequest(
            rating = _uiState.value.rate ?: 1,
            description = _uiState.value.review,
            username = "",
            itemId = id,
            itemType = "Book"
        )
    ).onEach { resource ->
        when (resource) {
            is Resource.Error -> {
                _uiState.update { it.copy(loading = false) }
                showSnackbar(message = resource.message ?: "Unknown Error", label = "Retry", action = {
                    onEvent(
                        EventBookDetail.SubmitReview
                    )
                })
            }

            is Resource.Loading -> _uiState.update { it.copy(loading = true) }
            is Resource.Success -> {
                _uiState.update { it.copy(loading = false, review = "", rate = null) }
                showSnackbar(message = resource.data ?: "Review added successfully")
                fetchReview()
            }
        }
    }.launchIn(viewModelScope)

    private fun deleteReview(reviewId: String) =
        deleteReviewUseCase.invoke(reviewId).onEach { resource ->
            when (resource) {
                is Resource.Error -> {
                    _uiState.update { it.copy(loading = false) }
                    showSnackbar(
                        message = resource.message ?: "Unknown Error While Deleting"
                    )
                }
                is Resource.Loading -> _uiState.update { it.copy(loading = true) }
                is Resource.Success -> {
                    _uiState.update { it.copy(loading = false, review = "", rate = null) }
                    showSnackbar(message = resource.data ?: "Review added successfully")
                    fetchReview()
                }
            }
        }.launchIn(viewModelScope)

    private fun addToCart() {
//        Log.d("VM_BOOK_DETAIL", "add to cart $id")
        viewModelScope.launch {
            addToCartUseCase(
                id = uiState.value.fetchedBook.id,
                productType = uiState.value.productType ?: ProductType.Book,
            ).collect {

            }
        }
    }

    private fun showSnackbar(message: String, label: String? = null, action: () -> Unit = {}) {
        viewModelScope.launch {
            snackbarManager.showSnackbar(
                message = SnackbarMessage(
                    message = message,
                    actionLabel = label,
                    onActionPerformed = action
                )
            )
        }
    }
}
