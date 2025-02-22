package com.nabssam.bestbook.presentation.ui.account.auth.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nabssam.bestbook.presentation.ui.account.auth.AuthEvent
import com.nabssam.bestbook.presentation.ui.account.auth.AuthState

@Composable
fun ExamInfoStep(
    state: AuthState,
    onEvent: (AuthEvent) -> Unit,
    validate: () -> Boolean,
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
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(state.allTargetExam) { it ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable {
                            onEvent(AuthEvent.UpdateUserTargetExam(it))
                        },
                    colors = CardDefaults.cardColors(
                        containerColor = if (state.userTargetExams.contains(it)) {
                            MaterialTheme.colorScheme.primaryContainer
                        } else {
                            MaterialTheme.colorScheme.background
                        }
                    ),
                    border = if (state.userTargetExams.contains(it))
                        BorderStroke(width = 2.dp, color = MaterialTheme.colorScheme.primary)
                    else
                        BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.secondary),
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(text = it /*modifier = Modifier.padding(16.dp)*/)
                        if (state.userTargetExams.contains(it)) {
                            Icon(
                                Icons.Filled.CheckCircle,
                                contentDescription = "Selected",
                                //modifier = Modifier.align(Alignment.End)
                            )
                        }
                    }
                }

            }
        }

        OutlinedTextField(
            value = state.targetYear.toString(),
            onValueChange = {
                onEvent(AuthEvent.UpdateTargetYear(it))
            },
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


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ExamInfoStepPreview() {
    ExamInfoStep(
        state = AuthState(),
        onEvent = {},
        validate = { true }
    )
}
