package com.nabssam.bestbook.presentation.ui.book.bookList

import com.nabssam.bestbook.domain.model.Book

data class StateBookList(

    val fetchingBooks: Boolean = false,
    val fetchedBooks: List<Book>? = emptyList(),
    val error: String? = null
//    val errorBooks: BookListError? = null,

)

/*sealed class BookListState {
    object Loading : BookListState()
    data class Success(val books: List<Book>) : BookListState()
    data class Error(val error: BookListError) : BookListState()
}



private val _state = MutableStateFlow<BookListState>(BookListState.Loading)
val state = _state.asStateFlow()

*/
