package com.nabssam.bestbook.presentation.ui.book.bookDetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.nabssam.bestbook.utils.Resource
import com.nabssam.bestbook.domain.repository.BookRepository
import com.nabssam.bestbook.presentation.navigation.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelBookDetail @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val bookRepository: BookRepository
) : ViewModel() {
    private val book = savedStateHandle.toRoute<Route.BookDetail>()

    init {
        fetchBookDetail()
    }

    private val _state = MutableStateFlow(StateBookDetail())
    val state = _state.asStateFlow()

    private fun fetchBookDetail() {
        viewModelScope.launch {
            bookRepository.getBookById(book.bookId).collect { resource ->
                
                when (resource) {

                    is Resource.Loading -> {
                        _state.value = state.value.copy(fetchingBook = true)
                    }

                    is Resource.Success -> {
                        _state.value = state.value.copy(
                            fetchingBook = false,
                            fetchedBook = resource.data
                        )
                    }

                    is Resource.Error -> {
                        _state.value = state.value.copy(
                            fetchingBook = false,
                            errorBook = resource.message.toString()
                        )
                    }
                }
            }
        }

    }
}