package com.nabssam.bestbook.presentation.ui.book.bookDetail

import com.nabssam.bestbook.domain.model.Book

data class StateBookDetail(

    //val bookId: Int = 0,
    val fetchingBook: Boolean = false,
    val fetchedBook: Book? = Book(),
    val errorBook: String = "",

)

