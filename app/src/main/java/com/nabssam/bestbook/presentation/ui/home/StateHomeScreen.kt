package com.nabssam.bestbook.presentation.ui.home

import com.nabssam.bestbook.domain.model.Banner
import com.nabssam.bestbook.domain.model.Book
import com.nabssam.bestbook.domain.model.Exam
import com.nabssam.bestbook.domain.model.Pyq
import com.nabssam.bestbook.domain.model.Quiz

data class StateHomeScreen(

    var fetchingBanners: Boolean = true,
    var fetchedBanners: List<Banner> = emptyList(),
    var errorBanners: String? = null,

    var fetchedExams: List<Exam> = emptyList(),
    var examError: String? = null,
    var examLoading: Boolean = true,


    var fetchingBooks: Boolean = true,
    var fetchedBooks: List<Book> = emptyList(),
    var errorBooks: String? = null,

    var fetchingPyq: Boolean = false,
    var fetchedPyq: List<Book> = emptyList(),
    var errorPyq: String? = null,

    val fullScreenError:String? = null,

    val randomTarget: String? = null,
)

/*
sealed class UiStateHome {
    data object IsLoading: UiStateHome()
    data class Idle(var data: StateHomeScreen): UiStateHome()
    data class Error(var message: String): UiStateHome()
}
*/
