/*
package com.nabssam.bestbook.presentation.addnote

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.nabssam.bestbook.domain.models.Notes
import kotlinx.serialization.Serializable

@Serializable
data object AddNotesScreen

@Composable
fun AddNoteScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    state: StateAddNoteScreen,
    event: (EventAddNoteScreen) -> Unit
) {
    LaunchedEffect(state) {
        when {
            state.savingNotes -> {
                //CircularProgressIndicator()
                Log.d("AddNoteScreen", "Loading: $state")
            }

            state.notesSaved == "Notes saved" -> {
                Log.d("AddNoteScreen", "Notes saved: $state")
                navController.popBackStack()
            }

            state.notesError == "Unable to save notes" -> {
                //Text(text = "notesError")
                Log.d("AddNoteScreen", "Notes error: $state")
            }
        }
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(32.dp)
    ) {
        TextField(
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            value = state.notesTitle,
            onValueChange = {
                event(EventAddNoteScreen.NoteTitle(it))
            }
        )
        TextField(
            modifier = modifier.fillMaxWidth(),
            value = state.notesDescription,
            onValueChange = {
                event(EventAddNoteScreen.NoteDescription(it))
            },
            minLines = 4,
        )
        Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            FilterChip(
                selected = state.label == "Low",
                onClick = { event(EventAddNoteScreen.NotesLabel("Low")) },
                label = {
                    Text(
                        text = "Low",
                        color = if (state.label == "Low")
                            Color.White
                        else
                            Color.Red
                    )
                },
                colors = FilterChipDefaults.filterChipColors(Color.Red.copy(alpha = 0.1f))

            )
            FilterChip(
                selected = state.label == "High",
                onClick = { event(EventAddNoteScreen.NotesLabel("High")) },
                label = {
                    Text(
                        text = "High",
                        color = if (state.label == "High")
                            Color.White
                        else
                            Color.Green.copy(alpha = 1f)
                    )
                },
                colors = FilterChipDefaults.filterChipColors(Color.Green.copy(alpha = 0.1f))
            )
        }
        Button(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RectangleShape,
            onClick = {
                Log.d("AddNoteScreen", "AddNoteScreen: $state")
                val notes = Notes(state.notesTitle, state.notesDescription, state.label)
                event(EventAddNoteScreen.SaveNote(notes))
            },
        ) {
            Text("Save")
        }

        if (state.savingNotes)
            CircularProgressIndicator()

    }
}
*/
