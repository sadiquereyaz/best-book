package com.nabssam.bestbook.presentation.ui.book.bookList

import com.nabssam.bestbook.domain.model.Book

data class StateBookList(

    //val fetchingTargetExam: Boolean = true,
    val examList: List<String> = emptyList(),

    val fetchingBooks: Boolean = true,
    val fetchedBooks: List<Book> = emptyList(),

    val userTargetExam: String? = null,

    val loading: Boolean = true,
    val errorMessage: String? = null

)

