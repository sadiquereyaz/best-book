package com.nabssam.bestbook.presentation.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class ViewModelHomeScreen : ViewModel() {


    private val _stateNote = MutableStateFlow(StateHomeScreen())
    val stateNote = _stateNote

    fun onEvent(){

    }
}

data class StateHomeScreen(
    val notesTitle: String = "",
)
