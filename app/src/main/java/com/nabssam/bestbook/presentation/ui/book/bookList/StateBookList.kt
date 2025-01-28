package com.nabssam.bestbook.presentation.ui.book.bookList

import com.nabssam.bestbook.domain.model.Book
import com.nabssam.bestbook.domain.model.Category

data class StateBookList(

    val fetchingBooks: Boolean = true,
    val fetchedBooks: List<Book> = emptyList(),

    val userTargetExam: String? = null,

    val errorMessage: String? = null

) {
    val categories: List<String> =
        fetchedBooks
            .map { it.exam }
            .filter { it.isNotEmpty() }
            .distinct()
            .sorted()
}

