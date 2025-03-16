package com.nabssam.bestbook.presentation.ui.book.bookList

sealed class EventBookList{
    data object FetchBook : EventBookList()
    data class SortBy(val exam: String?) : EventBookList()
    data object Retry : EventBookList()
    // events for search and filter
    data class UpdateSearchQuery(val query: String) : EventBookList()
    data class ToggleCategory(val category: String) : EventBookList()
    data class UpdatePriceRange(val range: ClosedFloatingPointRange<Float>) : EventBookList()
    data object ToggleDiscountedOnly : EventBookList()
    data object ResetFilters : EventBookList()
}

