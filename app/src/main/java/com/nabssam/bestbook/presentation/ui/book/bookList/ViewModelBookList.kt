package com.nabssam.bestbook.presentation.ui.book.bookList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/*_state.update{currentUiState->
    currentUiState.copy({
        fetchingBooks = true
    })
}*/

@HiltViewModel
class ViewModelBookList @Inject constructor(
    //private val bookRepository: BookRepository
) : ViewModel() {

    init {
        fetchBooks()
    }

    private val _state = MutableStateFlow(StateBookList())
    val state = _state.asStateFlow()



    private fun fetchBooks() {
        viewModelScope.launch {

           /* if (!internetConnection()) {
                _state.value = state.value.copy(error = "No Internet connection")
                return@launch
            } else {
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
                                error = resource.message.toString()
                            )
                        }
                    }
                }
            }*/
        }

    }
}

private fun internetConnection(): Boolean {
   /* val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkCapabilities = connectivityManager.activeNetwork ?: return false
    val activeNetwork = connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
    return when {
        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        activeNetwork.hasTranspo rt(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
        else -> false
    }*/

    return true
}
sealed class BookListError {
    object NetworkError : BookListError()
    data class ServerError(val message: String) : BookListError()
    // ... other error types
}


