package com.nabssam.bestbook.presentation.ui.book.bookList

import com.nabssam.bestbook.domain.model.Book

data class StateBookList(
    val fetchingBooks: Boolean = true,
    val fetchedBooks: List<Book> = emptyList(),
    val filteredBooks: List<Book> = emptyList(),
    val searchQuery: String = "",
    val selectedCategories: Set<String> = emptySet(),
    val userTargetExam: String? = null,
    val errorMessage: String? = null,
    val showOnlyDiscounted: Boolean = false,
    val priceRange: ClosedFloatingPointRange<Float> = 0f..5000f
) {
    val categories: List<String> =
        fetchedBooks
            .map { it.exam }
            .filter { it.isNotEmpty() }
            .distinct()
            .sorted()

    val minPrice: Float = fetchedBooks.minOfOrNull { it.price.toFloat() } ?: 0f
    val maxPrice: Float = fetchedBooks.maxOfOrNull { it.price.toFloat() } ?: 5000f
}