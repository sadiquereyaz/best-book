package com.nabssam.bestbook.presentation.ui.account.auth.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.nabssam.bestbook.presentation.ui.account.auth.AuthEvent
import com.nabssam.bestbook.presentation.ui.account.auth.AuthState
import kotlin.math.sin

@Composable
fun CredentialsStep(
    state: AuthState,
    onEvent: (AuthEvent) -> Unit,
    validate:()->Boolean
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Create Account",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(vertical = 32.dp)
        )
        // username
        OutlinedTextField(
            value = state.username,
            onValueChange = { onEvent(AuthEvent.UpdateUsername(it)) },
            label = { Text("Choose Username") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        //password
        PasswordField(
            value = state.password,
            onInput = { onEvent(AuthEvent.UpdatePassword(it)) }
        )
        Spacer(modifier = Modifier.height(16.dp))

        //confirm password
        PasswordField(
            value = state.confirmPassword,
            onInput = { onEvent(AuthEvent.UpdateConfirmPassword(it)) },
            label = "Confirm Password",
            imeAction = ImeAction.Done,
            onDoneAction = { if(validate()) onEvent(AuthEvent.NavigateNext) }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                onEvent(AuthEvent.NavigateNext)
                      },
            enabled = validate(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Next")
        }
    }
}
