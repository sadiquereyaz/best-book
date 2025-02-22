/*
package com.nabssam.bestbook.presentation.addnote

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nabssam.bestbook.common.util.Resource
import com.nabssam.bestbook.domain.models.Notes
import com.nabssam.bestbook.domain.repository.NotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelAddNotes @Inject constructor(
    private val notesRepository: NotesRepository
) :
    ViewModel() {

    private val _stateNote = MutableStateFlow(StateAddNoteScreen())
    val notesState = _stateNote

    fun saveNotes(note: Notes) {
        viewModelScope.launch {
            notesRepository.saveNotes(note).collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _stateNote.value = notesState.value.copy(savingNotes = true)
                        Log.d("NotesState", "ViewModelAddNotesScreen: Loading")
                    }

                    is Resource.Error -> {
                        _stateNote.value = notesState.value.copy(
                            savingNotes = false,
                            notesError = resource.message.toString()
                        )
                        Log.d("NotesState", "ViewModelAddNotesScreen: Error")
                    }

                    is Resource.Success -> {
                        _stateNote.value = notesState.value.copy(
                            savingNotes = false,
                            notesSaved = resource.data.toString()
                        )
                        Log.d("NotesState", "ViewModelAddNotesScreen: Success")
                    }
                }
            }
        }
    }

    fun onEvent(event: EventAddNoteScreen) {
        when (event) {
            is EventAddNoteScreen.NoteTitle -> {
                _stateNote.value = notesState.value.copy(
                    notesTitle = event.title
                )
            }

            is EventAddNoteScreen.NoteDescription -> {
                _stateNote.value = notesState.value.copy(
                    notesDescription = event.description
                )
            }

            is EventAddNoteScreen.NotesLabel -> {
                _stateNote.value = notesState.value.copy(
                    label = event.label
                )
            }

            is EventAddNoteScreen.SaveNote -> {
                saveNotes(event.notes)
            }
        }
    }
}*/
