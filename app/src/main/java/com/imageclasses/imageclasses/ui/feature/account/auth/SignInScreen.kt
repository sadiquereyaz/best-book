package com.imageclasses.imageclasses.ui.feature.account.auth

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.imageclasses.imageclasses.R
import com.imageclasses.imageclasses.auth.FirebaseAuth
import com.imageclasses.imageclasses.ui.navigation.Route
import kotlinx.serialization.Serializable



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignIn(navController: NavController) {
    val firebaseAuth = FirebaseAuth()
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(start = 20.dp, end = 20.dp, bottom = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Email Field
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            colors =  OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFFe83337),
                unfocusedBorderColor = MaterialTheme.colorScheme.tertiaryContainer,
                focusedTextColor = Color.Black,
                focusedLabelColor = Color.Black,
                unfocusedTextColor = Color.Black
            )
        )

        // Password Fieldif(
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            isError = password.length < 6 && password.isNotEmpty(),
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = (if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation()),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            colors =  OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFFe83337),
                unfocusedBorderColor = MaterialTheme.colorScheme.tertiaryContainer,
                focusedTextColor = Color.Black,
                focusedLabelColor = Color.Black,
                unfocusedTextColor = Color.Black
            ),


            supportingText = {
                if (password.length < 6 && password.isNotEmpty()) {
                    Text("password must be at least 6 characters", color = Color.DarkGray)
                }
            },
            trailingIcon = {
                val image = if (passwordVisible)
                    R.drawable.baseline_visibility_24
                else R.drawable.baseline_visibility_off_24

                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        painter = painterResource(image),
                        contentDescription = "Toggle password visibility"
                    )
                }
            }

        )
        var isDisable by remember { mutableStateOf(false) }

        if (email.isEmpty() || password.isEmpty() || password.length < 6 ) {
            isDisable = true
        } else isDisable = false

        Column(
            verticalArrangement = Arrangement.spacedBy(2.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    firebaseAuth.signIn(email, password, context = context) { success ->
                        if (success) {
                            navController.navigate(Route.MainGraph)
                        } else {
                            Toast.makeText(
                                context,
                                "check your username or password",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    }

                },
                enabled = !isDisable,

                modifier = Modifier

                    .fillMaxWidth(0.7f)
                    .padding(28.dp)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFe83337),
                    disabledContainerColor = Color.DarkGray
                )
            ) {
                Text(
                    "Sign In",
                    color = Color.White,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 20.sp
                )
            }
            TextButton(
                onClick = {
                    navController.navigate(Route.SignUp())
                }
            ) {
                Text(
                    text = "Dont' have an account? Sign up"
                )
            }
        }
    }
}