package com.nabssam.bestbook.presentation.ui.account.auth.claud.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.nabssam.bestbook.presentation.ui.account.auth.claud.AuthEvent
import com.nabssam.bestbook.presentation.ui.account.auth.claud.AuthState

@Composable
fun ExamInfoStep(
    state: AuthState,
    onEvent: (AuthEvent) -> Unit,
    validate: ()->Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Target Exams",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(vertical = 32.dp)
        )

        // Example list of common exams
        val commonExams = listOf("JEE Main", "NEET", "JEE Advanced", "CUET", "Board Exams")
        
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(commonExams) { exam ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = state.targetExams.contains(exam),
                        onCheckedChange = { checked ->
                            if (checked) {
                                onEvent(AuthEvent.AddExam(exam))
                            } else {
                                onEvent(AuthEvent.RemoveExam(exam))
                            }
                        }
                    )
                    Text(exam)
                }
            }
        }

        OutlinedTextField(
            value = state.targetYear,
            onValueChange = { onEvent(AuthEvent.UpdateTargetYear(it)) },
            label = { Text("Target Year") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { onEvent(AuthEvent.NavigateNext) },
            enabled = validate(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Next")
        }
    }
}
