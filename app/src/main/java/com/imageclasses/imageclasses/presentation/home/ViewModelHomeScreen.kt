package com.imageclasses.imageclasses.presentation.home

import androidx.lifecycle.ViewModel
import com.imageclasses.imageclasses.presentation.addnote.EventAddNoteScreen
import com.imageclasses.imageclasses.presentation.addnote.StateAddNoteScreen
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
