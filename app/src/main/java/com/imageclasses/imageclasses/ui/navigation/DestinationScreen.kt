package com.imageclasses.imageclasses.ui.navigation

import kotlinx.serialization.Serializable

sealed class DestinationScreen(var route: String, val title: String? = null) {

    object ots : DestinationScreen("one_time_screen")
    object intro : DestinationScreen("intro_screen")
    object auth_signup : DestinationScreen("signup_screen")
    object auth_signin :DestinationScreen("signin_screen")
    object mainApp : DestinationScreen("main_app")

    object home : DestinationScreen(route = "home_screen", title = "Image Classes"){
        fun createRoute(targetExam: String) = "home_screen/$targetExam"
    }
    object subscribedQuiz : DestinationScreen(route = "subscribed_quiz"){
    }
    object subscribedEbook : DestinationScreen(route = "subscribed_ebook")

    object userProfile : DestinationScreen("user_profile_screen")

    data class ProfileDetail (val userId: String) : DestinationScreen("user_profile_screen/$userId")
}