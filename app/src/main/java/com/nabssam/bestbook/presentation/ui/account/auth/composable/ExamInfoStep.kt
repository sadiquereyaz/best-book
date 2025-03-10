package com.nabssam.bestbook.presentation.ui.account.auth.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nabssam.bestbook.presentation.ui.account.auth.AuthEvent
import com.nabssam.bestbook.presentation.ui.account.auth.AuthState
import androidx.compose.material.icons.Icons
import com.nabssam.bestbook.presentation.ui.account.auth.composable.components.YearSelector


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
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 128.dp),
            //contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(state.allTargetExam) { it ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        //.padding(8.dp)
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
        Spacer(modifier = Modifier.weight(1f))
        YearSelector(state, onEvent)
        Button(
            onClick = { onEvent(AuthEvent.NavigateNext) },
            enabled = validate(),
            modifier = Modifier.fillMaxWidth(),
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
