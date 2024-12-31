package com.imageclasses.imageclasses.presentation.addnote

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class ViewModelAddNotesScreen : ViewModel(){

    private val _stateNote = MutableStateFlow(StateAddNoteScreen())
    val stateNote = _stateNote

    fun onEvent(event: EventAddNoteScreen){
        when(event){
            is EventAddNoteScreen.NoteTitle -> {
                _stateNote.value = stateNote.value.copy(
                    notesTitle = event.title
                )
            }
            is EventAddNoteScreen.NoteDescription -> {
                _stateNote.value = stateNote.value.copy(
                    notesDescription = event.description
                )
            }
            is EventAddNoteScreen.NotesLabel -> {
                _stateNote.value = stateNote.value.copy(
                    label = event.label
                )
            }
        }
    }
}