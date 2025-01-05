package com.nabssam.bestbook.presentation.ui.home

import Quiz
import com.nabssam.bestbook.domain.model.Banner
import com.nabssam.bestbook.domain.model.Book

data class StateHomeScreen(

    val gettingBanners: Boolean = false,
    val fetchedBanners: List<Banner>? = emptyList(),
    val errorBanners: String = "",

    val fetchingBooks: Boolean = false,
    val fetchedBooks: List<Book>? = emptyList(),
    val errorBooks: String = "",

    val gettingQuizzes: Boolean = false,
    val fetchedQuizzes: List<Quiz>? = emptyList(),
    val errorQuizzes: String = ""
)
