package com.nabssam.bestbook.presentation.ui.book.all_review

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.nabssam.bestbook.domain.usecase.review.AddReviewUseCase
import com.nabssam.bestbook.domain.usecase.review.DeleteReviewUseCase
import com.nabssam.bestbook.domain.usecase.review.GetBookReviewsUseCase
import com.nabssam.bestbook.presentation.navigation.Route
import com.nabssam.bestbook.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

private const val TAG = "ALL_REVIEW_VM"

@HiltViewModel
class AllReviewViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getBookReviewsUseCase: GetBookReviewsUseCase,
    private val deleteReviewUseCase: DeleteReviewUseCase,
    private val addReviewUseCase: AddReviewUseCase
) : ViewModel() {

    private var id: String? = null

    private val _state = MutableStateFlow(ReviewUiState())
    val uiState = _state.asStateFlow()

    init {
        try {
            id = savedStateHandle.toRoute<Route.AllReviewRoute>().bookId
        } catch (e: Exception) {
            Log.e(TAG, "Book Id error: ${e.message}")
        }
        fetchReview()
    }

    private fun fetchReview() =
        id?.let { bookId ->
            getBookReviewsUseCase.invoke(bookId, false).onEach { resource ->
                when (resource) {
                    is Resource.Error -> _state.update {
                        it.copy(error = resource.message, isLoading = false)
                    }

                    is Resource.Loading -> _state.update { it.copy(isLoading = true) }
                    is Resource.Success -> _state.update {
                        it.copy(
                            reviewsList = resource.data ?: emptyList(),
                            isLoading = false,
                            error = null
                        )
                    }
                }
            }.launchIn(viewModelScope)
        } ?: _state.update { it.copy(error = "Book Id not found") }

    fun deleteReview(reviewId: String) = deleteReviewUseCase.invoke(reviewId).onEach { resource ->
        when (resource) {
            is Resource.Error -> _state.update {
                it.copy(error = resource.message, isLoading = false)
            }

            is Resource.Loading -> _state.update { it.copy(isLoading = true) }
            is Resource.Success -> _state.update {
                it.copy(
                    reviewsList = it.reviewsList,
                    isLoading = false,
                    error = null
                )
            }
        }
    }
}