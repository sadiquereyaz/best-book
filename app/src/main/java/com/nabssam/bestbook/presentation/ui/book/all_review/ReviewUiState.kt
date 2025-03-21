package com.nabssam.bestbook.presentation.ui.book.all_review

import com.nabssam.bestbook.domain.model.Review

data class ReviewUiState(
    val isLoading: Boolean = false,
    val reviewsList: List<Review> = emptyList(),
    val error: String? = null,
)