package com.nabssam.bestbook.presentation.ui.account.auth.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.nabssam.bestbook.R
import com.nabssam.bestbook.presentation.ui.account.auth.AuthEvent
import com.nabssam.bestbook.presentation.ui.account.auth.AuthState

@Composable
fun LoginStep(
    state: AuthState,
    onEvent: (AuthEvent) -> Unit,
    validate: () -> Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

            Image(
                imageVector = ImageVector.vectorResource(if (isSystemInDarkTheme()) R.drawable.logo_night else R.drawable.logo),
                "",
                modifier = Modifier.fillMaxWidth(0.6f)
                    )
        Spacer(modifier = Modifier.height(24.dp))
            OutlinedTextField(
                value = state.username,
                onValueChange = { if (it.length < 10) onEvent(AuthEvent.UpdateUsername(it)) },
                label = { Text("Username") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // password
            PasswordField(
                value = state.password,
                onInput = { onEvent(AuthEvent.UpdatePassword(it)) },
                label = "Password",
                imeAction = ImeAction.Done,
                onDoneAction = { if (validate()) onEvent(AuthEvent.SignIn) }
            )

            Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { onEvent(AuthEvent.SignIn) },
            //modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            ),
            modifier = Modifier
                .fillMaxWidth()
                .background(
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFF223876), Color(0xFF1e429f),Color(0xFF5C1ED9)),
                ),
                shape = RoundedCornerShape(100)
                ),
            enabled = validate()
        ) {
            Text("Login")
        }

            Spacer(modifier = Modifier.height(4.dp))

            OutlinedButton(
                onClick = { onEvent(AuthEvent.NavigateNext) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Sign Up")
            }

            TextButton(
                onClick = { onEvent(AuthEvent.ForgetPassword) }
            ) {
                Text("Forgot Password? Reset Now")
            }
        }
    }