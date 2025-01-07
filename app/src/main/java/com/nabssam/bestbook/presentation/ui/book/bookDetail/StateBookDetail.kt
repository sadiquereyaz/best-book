package com.nabssam.bestbook.presentation.ui.book.bookDetail

import com.nabssam.bestbook.domain.model.Book

data class StateBookDetail(
    val id: String,
    var fetchedBook: Book? = null,
)

