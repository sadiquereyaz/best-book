package com.nabssam.bestbook.presentation.ui.book.bookDetail

sealed class EventBookDetail{
    data object Retry : EventBookDetail()
    data object AddToCart : EventBookDetail()
    data object ButtonClick : EventBookDetail()
    data class BookTypeSelect(val btnState: ButtonType = ButtonType.ADD_TO_CART) : EventBookDetail()
}

