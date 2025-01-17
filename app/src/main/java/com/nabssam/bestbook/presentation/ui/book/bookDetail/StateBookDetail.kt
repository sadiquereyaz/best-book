package com.nabssam.bestbook.presentation.ui.book.bookDetail

import com.nabssam.bestbook.domain.model.Book

data class StateBookDetail(
    var loading: Boolean = true,
    var errorMessage: String? = null,
    var fetchedBook: Book = Book(),
    val isListFetching: Boolean = true,
    val fetchedList: List<Book> = emptyList(),
    val listError: String? = null,
)

