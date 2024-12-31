package com.nabssam.bestbook.ui.feature.bookDetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.nabssam.bestbook.ui.navigation.Route


/*
* Complex objects should be stored as data in a single source of truth,
* such as the data layer. Once you land on your destination after navigating,
* you can then load the required information from the single source of truth by using the passed ID.
* To retrieve the arguments in your ViewModel that's responsible for accessing the data layer, use the SavedStateHandle of the ViewModel:
* */


class BookDetailViewModel(
    savedStateHandle: SavedStateHandle,
    //private val userInfoRepository: UserInfoRepository
) : ViewModel() {

    private val book = savedStateHandle.toRoute<Route.BookDetail>()

    // Fetch the relevant book information from the data layer,
    // ie. bookInfoRepository, based on the passed bookId argument
    //private val bookInfo: Flow<BookModel> = bookInfoRepository.getUserInfo(book.bookId)

// …

}