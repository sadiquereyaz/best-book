package com.nabssam.bestbook.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nabssam.bestbook.utils.Resource
import com.nabssam.bestbook.domain.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelHome @Inject constructor(
    private val bookRepository: BookRepository
) : ViewModel() {

    init {
        fetchBooks()
    }

    private val _state = MutableStateFlow(StateHomeScreen())
    val state = _state.asStateFlow()


    private fun fetchBooks() {
        viewModelScope.launch {
            bookRepository.getAllBooks().collect { resource ->
                
                when (resource) {

                    is Resource.Loading -> {
                        _state.value = state.value.copy(fetchingBooks = true)
                    }

                    is Resource.Success -> {
                        _state.value = state.value.copy(
                            fetchingBooks = false,
                            fetchedBooks = resource.data
                        )
                    }

                    is Resource.Error -> {
                        _state.value = state.value.copy(
                            fetchingBooks = false,
                            errorBooks = resource.message.toString()
                        )
                    }
                }
            }
        }

    }
}