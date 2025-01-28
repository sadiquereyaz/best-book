package com.nabssam.bestbook.presentation.ui.address

import com.nabssam.bestbook.domain.model.Book
import com.nabssam.bestbook.domain.model.Exam
import com.nabssam.bestbook.domain.model.Quiz

data class StateAddressScreen(

    var fetchingBanners: Boolean = true,
    var fetchedBanners: List<String> = emptyList(),
//    var fetchedBanners: List<Banner> = emptyList(),   //TODO: later implementation
    var errorBanners: String? = null,

    var fetchedExams: List<Exam> = emptyList(),
    var examError: String? = null,
    var examLoading: Boolean = true,


    var fetchingBooks: Boolean = true,
    var fetchedBooks: List<Book> = emptyList(),
    var errorBooks: String? = null,

    var gettingQuizzes: Boolean = false,
    var fetchedQuizzes: List<Quiz> = emptyList(),
    var errorQuizzes: String? = null,

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
