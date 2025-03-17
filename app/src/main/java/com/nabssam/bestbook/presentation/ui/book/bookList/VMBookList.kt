package com.nabssam.bestbook.presentation.ui.book.bookList

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.nabssam.bestbook.domain.usecase.book.GetAllBookUseCase
import com.nabssam.bestbook.domain.usecase.book.GetAllTargetUC
import com.nabssam.bestbook.domain.usecase.exam_std.GetUserTargetsUC
import com.nabssam.bestbook.presentation.navigation.Route
import com.nabssam.bestbook.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "BOOK_LIST_VM"

@HiltViewModel
class VMBookList @Inject constructor(
    private val getAllBookUseCase: GetAllBookUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val targetExam:String? = savedStateHandle.toRoute<Route.AllBookRoute>().targetExam


    private val _state = MutableStateFlow(StateBookList())
    val state = _state.asStateFlow()

    init {
        fetchBooks()
    }

    fun onEvent(event: EventBookList) {
        when (event) {
            is EventBookList.FetchBook -> fetchBooks()
            is EventBookList.Retry -> fetchBooks()
            is EventBookList.SortBy -> updateSorting(event.exam)        // redundant

            // Handle new search and filter events
            is EventBookList.UpdateSearchQuery -> updateSearchQuery(event.query)
            is EventBookList.ToggleCategory -> toggleCategory(event.category)
            is EventBookList.UpdatePriceRange -> updatePriceRange(event.range)
            is EventBookList.ToggleDiscountedOnly -> toggleDiscountedOnly()
            is EventBookList.ResetFilters -> resetFilters()
        }
    }

    private fun fetchBooks() = getAllBookUseCase.invoke().onEach { resource ->
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
                //Log.d(TAG, "Fetched books: ${books.map { it.exam }}")
                _state.update {
                    it.copy(
                        fetchingBooks = false,
                        fetchedBooks = books,
                        filteredBooks = books, // Initially, filtered books = all books
                        errorMessage = null,
                        priceRange = (books.minOfOrNull { book -> book.price.toFloat() }
                            ?: 0f)..
                                (books.maxOfOrNull { book -> book.price.toFloat() }
                                    ?: 5000f)
                    )
                }
                targetExam?.let {
                updateSearchQuery(it)}
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
    }.launchIn(viewModelScope)

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

    /**
     * Applies filtering criteria to the list of fetched books based on the current state.
     *
     * This function filters the `fetchedBooks` in the current state based on the following criteria:
     *
     * 1. **Search Query:**
     *    - If the `searchQuery` is empty, all books are considered a match.
     *    - If the `searchQuery` is not empty, a book is considered a match if its `name` or `description` (if available)
     *      contains the `searchQuery` (case-insensitive).
     *
     * 2. **Selected Categories:**
     *    - If `selectedCategories` is empty, all books are considered a match.
     *    - If `selectedCategories` is not empty, a book is considered a match if its `exam` property is present in the
     *      `selectedCategories` set.
     *
     * 3. **Price Range:**
     *    - A book is considered a match if its `price` (converted to Float) falls within the `priceRange`.
     *
     * 4. **Discounted Books:**
     *    - If `showOnlyDiscounted` is `false`, all books are considered a match.
     *    - If `showOnlyDiscounted` is `true`, a book is considered a match only if its `hardCopyDis` property is greater than 0.
     *
     * The function updates the `filteredBooks` property in the state with the filtered list.
     * This operation is performed within a coroutine launched in the `viewModelScope`.
     */
    private fun applyFilters() {
        viewModelScope.launch {
            _state.update { currentState ->
                val query = currentState.searchQuery
                val filteredBooks = currentState.fetchedBooks.filter { book ->
                    val matchesSearch =
                        query.isEmpty() ||
                                book.exam?.contains(query, true) ?: false ||
                                book.author?.contains(query, true) ?: false ||
                                book.name.contains(query, ignoreCase = true) ||
                                book.description?.contains(query, ignoreCase = true) ?: false

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
                    book.averageRate ?: 0.0
                }
            }
            currentState.copy(filteredBooks = sortedBooks)
        }
    }

}

