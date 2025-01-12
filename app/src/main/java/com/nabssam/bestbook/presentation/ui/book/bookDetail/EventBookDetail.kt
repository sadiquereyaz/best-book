package com.nabssam.bestbook.presentation.ui.book.bookDetail

sealed class EventBookDetail{
    data object Retry : EventBookDetail()
    data object AddToCart : EventBookDetail()
}

