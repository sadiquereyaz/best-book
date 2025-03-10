package com.nabssam.bestbook.presentation.ui.account.auth.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nabssam.bestbook.presentation.theme.BestBookTheme
import com.nabssam.bestbook.presentation.ui.account.auth.AuthEvent
import com.nabssam.bestbook.presentation.ui.account.auth.AuthState
import com.nabssam.bestbook.utils.DummyData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EducationInfoStep(
    state: AuthState,
    onEvent: (AuthEvent) -> Unit,
    validate:()->Boolean
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(state.currentClass) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Education Information",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(vertical = 32.dp)
        )

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                value = selectedOptionText,
                onValueChange = {},
                readOnly = true,
                label = { Text("Current Class") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                state.standardList.forEach { selectionOption ->
                    DropdownMenuItem(
                        text = { Text(text = selectionOption.name) },
                        onClick = {
                            selectedOptionText = selectionOption.name
                            expanded = false
                            onEvent(AuthEvent.OnClassSelect(selectionOption.name, selectionOption.exams))
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = state.schoolName,
            onValueChange = { if(it.length < 22) onEvent(AuthEvent.UpdateSchool(it)) },
            label = { Text("School Name") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 2
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { onEvent(AuthEvent.NavigateNext) },
            enabled = state.currentClass.isNotEmpty() && state.schoolName.isNotEmpty(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Next")
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun EducationInfoStepPreview() {
    BestBookTheme {
        EducationInfoStep(
            state = AuthState(
                standardList = DummyData.standards,
            ),
            onEvent = {},
            validate = {true},
        )
    }
}
