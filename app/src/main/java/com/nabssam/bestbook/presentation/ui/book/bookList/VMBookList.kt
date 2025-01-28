package com.nabssam.bestbook.presentation.ui.book.bookList

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nabssam.bestbook.domain.usecase.book.GetAllBookUseCase
import com.nabssam.bestbook.domain.usecase.book.GetAllTargetUC
import com.nabssam.bestbook.domain.usecase.datastore.GetUserTargetsUC
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
    private val getUserTargetsUC: GetUserTargetsUC,
    private val getAllTargetUC: GetAllTargetUC
) : ViewModel() {

    private val _state = MutableStateFlow(StateBookList())
    val state = _state.asStateFlow()

    //private val targetExam = getTargetExamUseCase()

    init {
       // fetchCategories()
        fetchBooks()
    }

    fun onEvent(event: EventBookList) {
        when (event) {
            is EventBookList.FetchBook -> fetchBooks()
            is EventBookList.SortBy -> {
                _state.update { currentState ->
                    currentState.copy(
                        fetchedBooks = if (event.exam != null) {
                            // Sort by whether the book's exam matches event.exam
                            currentState.fetchedBooks.sortedByDescending { book ->
                                book.exam == event.exam
                            }
                        } else {
                            // Sort by rating when event.exam is null
                            currentState.fetchedBooks.sortedByDescending { book ->
                                book.rate?.points ?: 0.0
                            }
                        }
                    )
                }
            }

            is EventBookList.Retry -> {
                //fetchCategories()
                fetchBooks()
            }
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
                        _state.update {
                            Log.d("BOOK_LIST_VM", "Success: ${resource.data}")
                            it.copy(
                                fetchingBooks = false,
                                fetchedBooks = resource.data ?: emptyList(),
                                errorMessage = null
                            )
                        }
                        Log.d("BOOK_LIST_VM", "state: ${state.value.fetchedBooks}")
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
}

