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
    /**
     * A list of unique, sorted exam categories extracted from the fetched books.
     *
     * This property derives its value from the `fetchedBooks` list. It performs the following operations:
     * 1. **Maps**: Extracts the `exam` property (or an empty string if `exam` is null) from each book in `fetchedBooks`.
     * 2. **Filters**:  Keeps only the elements that are empty strings (indicating books with no specified exam category).
     * 3. **Distinct**: Removes duplicate entries, ensuring each category is listed only once.
     * 4. **Sorted**: Arranges the unique categories in ascending alphabetical order.
     *
     *  Note that in current implementation it returns list of empty strings if book doesn't contains exam property.
     */
    val categories: List<String> = fetchedBooks
            .map { it.exam ?: "" }
            .filter { it.isNotEmpty() }
            .distinct()
            .sorted()

    val minPrice: Float = fetchedBooks.minOfOrNull { it.price.toFloat() } ?: 0f
    val maxPrice: Float = fetchedBooks.maxOfOrNull { it.price.toFloat() } ?: 5000f
    val isFilterApplied: Boolean =
        priceRange != minPrice..maxPrice || selectedCategories.isNotEmpty() || showOnlyDiscounted
}