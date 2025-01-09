package com.nabssam.bestbook.presentation.ui.book.bookList

sealed class EventBookList{
    data object FetchBook : EventBookList()
    data class SortBy(val category: String?) : EventBookList()
    data object Retry : EventBookList()
}

