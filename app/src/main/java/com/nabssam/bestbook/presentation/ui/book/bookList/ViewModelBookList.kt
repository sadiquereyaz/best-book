package com.nabssam.bestbook.presentation.ui.book.bookList

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nabssam.bestbook.domain.usecase.book.GetAllBookUseCase
import com.nabssam.bestbook.domain.usecase.book.GetAllCategoryUseCase
import com.nabssam.bestbook.domain.usecase.datastore.GetUserTargetsUC
import com.nabssam.bestbook.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelBookList @Inject constructor(
    private val getAllBookUseCase: GetAllBookUseCase,
    private val getUserTargetsUC: GetUserTargetsUC,
    private val getAllCategoryUseCase: GetAllCategoryUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(StateBookList())
    val state = _state.asStateFlow()

    //private val targetExam = getTargetExamUseCase()

    init {
        fetchCategories()
        fetchBooks()
    }

    private fun fetchCategories() {
        viewModelScope.launch {
                getAllCategoryUseCase().collect { resource ->
                    when (resource) {
                        is Resource.Loading -> _state.update { it.copy(loading = true) }
                        is Resource.Error -> _state.update { it.copy(loading = false, errorMessage = resource.message) }
                        is Resource.Success -> {
                            Log.d("CATEGORY", resource.data.toString());
                            _state.update { currentState -> currentState.copy(examList = resource.data ?: emptyList(), loading = false) }
                        }
                    }
                }


        }
    }

    fun onEvent(event: EventBookList) {
        when (event) {
            is EventBookList.FetchBook -> fetchBooks()
            is EventBookList.SortBy -> {
                Log.d("BOOK_LIST_VM", event.id ?: "category id is null")
                _state.update { currentState ->
                    currentState.copy(
                        fetchedBooks = currentState.fetchedBooks
                            .sortedByDescending { book ->
                            if (event.id != null) {
                                book.name == event.id
                            } else {
                                book.exam == _state.value.userTargetExam
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
                            it.copy(
                                fetchingBooks = true,
                                errorMessage = null
                            )
                        }
                    }

                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                fetchingBooks = false,
                                fetchedBooks = resource.data ?: emptyList(),
                                errorMessage = null
                            )
                        }
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

