package com.nabssam.bestbook.presentation.ui.sample
/*

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun SignUpScreen(viewModel: SignUpViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (uiState) {
            is SignUpUiState.Idle -> {
                val data = (uiState as SignUpUiState.Idle).data
                SignUpForm(
                    username = data.username,
                    onUsernameChange = viewModel::onUsernameChange,
                    password = data.password,
                    onPasswordChange = viewModel::onPasswordChange,
                    termsAccepted = data.termsAccepted,
                    onTermsAcceptedChange = viewModel::onTermsAcceptedChange,
                    onSignUpClick = viewModel::signUp
                )
            }
            is SignUpUiState.Loading -> {
                CircularProgressIndicator()
            }
            is SignUpUiState.Error -> {
                Text(text = (uiState as SignUpUiState.Error).message, color = Color.Red)
                Spacer(modifier = Modifier.height(8.dp))
                val data = (uiState as SignUpUiState.Idle).data
                SignUpForm(
                    username = data.username,
                    onUsernameChange = viewModel::onUsernameChange,
                    password = data.password,
                    onPasswordChange = viewModel::onPasswordChange,
                    termsAccepted = data.termsAccepted,
                    onTermsAcceptedChange = viewModel::onTermsAcceptedChange,
                    onSignUpClick = viewModel::signUp
                )
            }
        }
    }
}

@Composable
fun SignUpForm(
    username: String,
    onUsernameChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    termsAccepted: Boolean,
    onTermsAcceptedChange: (Boolean) -> Unit,
    onSignUpClick: () -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        TextField(
            value = username,
            onValueChange = onUsernameChange,
            label = { Text("Username") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = password,
            onValueChange = onPasswordChange,
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = termsAccepted,
                onCheckedChange = onTermsAcceptedChange
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Accept Terms and Conditions")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onSignUpClick) {
            Text("Sign Up")
        }
    }
}*/
