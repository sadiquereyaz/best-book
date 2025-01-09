package com.nabssam.bestbook.presentation.ui.book.bookList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nabssam.bestbook.domain.usecase.book.GetAllBookUseCase
import com.nabssam.bestbook.domain.usecase.book.GetAllCategoryUseCase
import com.nabssam.bestbook.domain.usecase.datastore.GetTargetExamUseCase
import com.nabssam.bestbook.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelBookList @Inject constructor(
    private val getAllBookUseCase: GetAllBookUseCase,
    getTargetExamUseCase: GetTargetExamUseCase,
    private val getAllCategoryUseCase: GetAllCategoryUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(StateBookList())
    val state = _state.asStateFlow()

    private val targetExam = getTargetExamUseCase().filterNot { it.isEmpty() }

    init {
        fetchCategories()
        fetchBooks()
    }

    private fun fetchCategories() {
        viewModelScope.launch {
            targetExam.collect { targetExam ->
                getAllCategoryUseCase().collect { resource ->
                    when (resource) {

                        is Resource.Loading -> {
                            _state.update {
                                it.copy(
                                    loading = true
                                )
                            }
                        }

                        is Resource.Error -> {
                            _state.update {
                                it.copy(
                                    loading = false,
                                    errorMessage = resource.message
                                )
                            }
                        }

                        is Resource.Success -> {
                            _state.update { currentState ->
                                currentState.copy(
                                    examList = resource.data!!,
                                    loading = false,
                                    userTargetExam = targetExam
                                )
                            }
                        }
                    }
                }
            }

        }
    }

    fun onEvent(event: EventBookList) {
        when (event) {
            is EventBookList.FetchBook -> {
                fetchBooks()
            }

            is EventBookList.SortBy -> {
                _state.update { currentState ->
                    currentState.copy(
                        fetchedBooks = currentState.fetchedBooks.sortedByDescending { book ->
                            if (event.category != null) {
                                book.category == event.category
                            } else {
                                book.category == _state.value.userTargetExam
                            }
                        }
                    )
                }
            }

            is EventBookList.Retry -> {
                fetchCategories()
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
                            it.copy(fetchingBooks = true)
                        }
                    }

                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                fetchingBooks = false,
                                fetchedBooks = resource.data ?: emptyList()
                            )
                        }
                    }

                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                fetchingBooks = false,
                                errorMessage = resource.message
                            )
                        }
                    }
                }

            }

        }

    }
}

