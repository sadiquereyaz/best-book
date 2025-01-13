package com.nabssam.bestbook.presentation.ui.account.auth.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.nabssam.bestbook.R
import com.nabssam.bestbook.presentation.ui.account.auth.AuthEvent
import com.nabssam.bestbook.presentation.ui.account.auth.AuthState

@Composable
fun CredentialsStep(
    state: AuthState,
    onEvent: (AuthEvent) -> Unit
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

        OutlinedTextField(
            value = state.username,
            onValueChange = { onEvent(AuthEvent.UpdateUsername(it)) },
            label = { Text("Choose Username") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        PasswordField(
            value = state.password,
            onEvent = { onEvent(AuthEvent.UpdatePassword(it)) }
        )
        Spacer(modifier = Modifier.height(16.dp))

        PasswordField(
            value = state.confirmPassword,
            onEvent = { onEvent(AuthEvent.UpdateConfirmPassword(it)) },
            label = "Confirm Password",
            imeAction = ImeAction.Done
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { onEvent(AuthEvent.NavigateNext) },
            enabled = state.username.isNotEmpty() && state.password.isNotEmpty(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Next")
        }
    }
}

@Composable
fun PasswordField(
    value: String,
    onEvent: (String) -> Unit, // Use a lambda for onValueChange
    label: String = "Create Password", // Add a label parameter with a default value
    imeAction: ImeAction = ImeAction.Next // Add an imeAction parameter with a default value
) {
    var passwordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = { onEvent(it) }, // Pass onValueChange to OutlinedTextField
        label = { Text(label) }, // Use the label parameter
        modifier = Modifier.fillMaxWidth(),
        visualTransformation = if (passwordVisible)
            VisualTransformation.None
        else
            PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = imeAction
        ),
        trailingIcon = {
            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                val image = if (passwordVisible)
                    R.drawable.baseline_visibility_24
                else
                    R.drawable.baseline_visibility_off_24 // Use correct drawable name
                Icon(painter = painterResource(image),
                    contentDescription = "Toggle password visibility")
            }
        },
    )
}