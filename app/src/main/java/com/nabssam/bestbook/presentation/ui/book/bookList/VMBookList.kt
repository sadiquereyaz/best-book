package com.nabssam.bestbook.presentation.ui.book.bookList

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nabssam.bestbook.domain.usecase.book.GetAllBookUseCase
import com.nabssam.bestbook.domain.usecase.book.GetAllTargetUC
import com.nabssam.bestbook.domain.usecase.exam_std.GetUserTargetsUC
import com.nabssam.bestbook.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VMBookList @Inject constructor(
    private val getAllBookUseCase: GetAllBookUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(StateBookList())
    val state = _state.asStateFlow()

    init {
        fetchBooks()
    }

    fun onEvent(event: EventBookList) {
        when (event) {
            is EventBookList.FetchBook -> fetchBooks()
            is EventBookList.Retry -> fetchBooks()
            is EventBookList.SortBy -> updateSorting(event.exam)

            // Handle new search and filter events
            is EventBookList.UpdateSearchQuery -> updateSearchQuery(event.query)
            is EventBookList.ToggleCategory -> toggleCategory(event.category)
            is EventBookList.UpdatePriceRange -> updatePriceRange(event.range)
            is EventBookList.ToggleDiscountedOnly -> toggleDiscountedOnly()
            is EventBookList.ResetFilters -> resetFilters()
        }
    }

    private fun fetchBooks() {
        viewModelScope.launch {
            getAllBookUseCase().collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _state.update {
                            it.copy(
                                fetchingBooks = true,
                                errorMessage = null
                            )
                        }
                    }

                    is Resource.Success -> {
                        val books = resource.data ?: emptyList()
                        _state.update {
                            it.copy(
                                fetchingBooks = false,
                                fetchedBooks = books,
                                filteredBooks = books, // Initially, filtered books = all books
                                errorMessage = null,
                                priceRange = (books.minOfOrNull { book -> book.price.toFloat() } ?: 0f)..
                                        (books.maxOfOrNull { book -> book.price.toFloat() } ?: 5000f)
                            )
                        }
                        // Apply any existing filters
                        applyFilters()
                    }

                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                fetchingBooks = false,
                                errorMessage = resource.message,
                            )
                        }
                    }
                }

            }

        }

    }

    private fun updateSorting(exam: String?) {
        _state.update { currentState ->
            val sortedBooks = if (exam != null) {
                // Sort by whether the book's exam matches exam
                currentState.filteredBooks.sortedByDescending { book ->
                    book.exam == exam
                }
            } else {
                // Sort by rating when exam is null
                currentState.filteredBooks.sortedByDescending { book ->
                    book.rate?.points ?: 0.0
                }
            }
            currentState.copy(filteredBooks = sortedBooks)
        }
    }

    private fun updateSearchQuery(query: String) {
        _state.update { it.copy(searchQuery = query) }
        applyFilters()
    }

    private fun toggleCategory(category: String) {
        _state.update { currentState ->
            val updatedCategories = currentState.selectedCategories.toMutableSet()
            if (category in updatedCategories) {
                updatedCategories.remove(category)
            } else {
                updatedCategories.add(category)
            }
            currentState.copy(selectedCategories = updatedCategories)
        }
        applyFilters()
    }

    private fun updatePriceRange(range: ClosedFloatingPointRange<Float>) {
        _state.update { it.copy(priceRange = range) }
        applyFilters()
    }

    private fun toggleDiscountedOnly() {
        _state.update { it.copy(showOnlyDiscounted = !it.showOnlyDiscounted) }
        applyFilters()
    }

    private fun resetFilters() {
        _state.update { currentState ->
            currentState.copy(
                searchQuery = "",
                selectedCategories = emptySet(),
                showOnlyDiscounted = false,
                priceRange = currentState.minPrice..currentState.maxPrice,
                filteredBooks = currentState.fetchedBooks
            )
        }
    }

    private fun applyFilters() {
        _state.update { currentState ->
            val filteredBooks = currentState.fetchedBooks.filter { book ->
                val matchesSearch = currentState.searchQuery.isEmpty() ||
                        book.name.contains(currentState.searchQuery, ignoreCase = true) ||
                        book.description?.contains(currentState.searchQuery, ignoreCase = true) == true

                val matchesCategory = currentState.selectedCategories.isEmpty() ||
                        book.exam in currentState.selectedCategories

                val matchesPrice = book.price.toFloat() >= currentState.priceRange.start &&
                        book.price.toFloat() <= currentState.priceRange.endInclusive

                val matchesDiscount = !currentState.showOnlyDiscounted ||
                        (book.hardCopyDis ?: 0) > 0

                matchesSearch && matchesCategory && matchesPrice && matchesDiscount
            }
            currentState.copy(filteredBooks = filteredBooks)
        }
    }
}

