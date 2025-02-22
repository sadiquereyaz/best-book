package com.nabssam.bestbook.presentation.ui.home

sealed class EventHomeScreen{
    data object FetchBook : EventHomeScreen()
    data object FetchBanner : EventHomeScreen()
    data object Initialize : EventHomeScreen()
//    data object FetchExams : EventHomeScreen()
}

