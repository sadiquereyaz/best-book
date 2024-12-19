package com.imageclasses.imageclasses.navigation

sealed class DestinationScreen(var route: String, val title: String? = null) {

    object ots : DestinationScreen("one_time_screen")
    object intro : DestinationScreen("intro_screen")
    object auth_signup : DestinationScreen("signup_screen")
    object auth_signin :DestinationScreen("signin_screen")
    object mainApp : DestinationScreen("main_app")

    object home : DestinationScreen(route = "home_screen", title = "Image Classes"){
        fun createRoute(targetExam: String) = "home_screen/$targetExam"
    }

    object userProfile : DestinationScreen("user_profile_screen")

    object bookList : DestinationScreen("book_list_screen/{speciality}") {
        fun createRoute(bookId: Int) = "book_list_screen/$bookId"
    }

}