package com.nabssam.bestbook.presentation.addnote

sealed class EventAddNoteScreen{
    data class NoteTitle(val title:String) : EventAddNoteScreen()
    data class NoteDescription(val description:String) : EventAddNoteScreen()
    data class NotesLabel(val label:String) : EventAddNoteScreen()
}
