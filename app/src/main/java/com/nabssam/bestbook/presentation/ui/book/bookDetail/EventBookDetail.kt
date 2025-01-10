package com.nabssam.bestbook.presentation.ui.book.bookDetail

import com.nabssam.bestbook.domain.model.Book

sealed class EventBookDetail{
    data object Retry : EventBookDetail()
    data object AddToCart : EventBookDetail()
}

