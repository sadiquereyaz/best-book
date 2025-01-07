package com.nabssam.bestbook.presentation.ui.productlist

import com.nabssam.bestbook.domain.model.Banner
import com.nabssam.bestbook.domain.model.Book

data class StateProductList(
    val fetchingBooks: Boolean = false,
    val fetchedBooks: List<Book>? = emptyList(),
    val errorBooks: String = "",
)
