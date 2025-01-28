package com.nabssam.bestbook.presentation.ui.address

sealed class EventAddressScreen{
    data object FetchBook : EventAddressScreen()
    data object FetchBanner : EventAddressScreen()
    data object Initialize : EventAddressScreen()
//    data object FetchExams : EventHomeScreen()
}

