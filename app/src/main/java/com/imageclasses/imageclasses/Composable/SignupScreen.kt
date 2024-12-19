package com.imageclasses.imageclasses.Composable

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.imageclasses.imageclasses.Auth.FirebaseAuth
import com.imageclasses.imageclasses.ui.theme.tertiaryContainerDark
import java.util.*

@Preview
@Composable
private fun he() {
    a()
}

@Composable
fun a() {
    SignUp(onSignUpSuccess = {})
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUp(modifier: Modifier = Modifier, onSignUpSuccess: () -> Unit) {
    val firebaseAuth = FirebaseAuth()
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var signUpError by remember { mutableStateOf<String?>(null) }

    // Dropdown options for class, entrance exam, and target year
    val classOptions = listOf("Class 12", "Class 10", "Class 9") // Example classes
    var selectedClass by remember { mutableStateOf(classOptions[0]) }

    val entranceExamOptions = listOf("JEE", "NEET", "CET") // Example entrance exams
    var selectedExam by remember { mutableStateOf(entranceExamOptions[0]) }

    val currentYear = Calendar.getInstance().get(Calendar.YEAR)
    val targetYearOptions = (currentYear..currentYear + 5).toList() // Example target years (next 5 years)
    var selectedTargetYear by remember { mutableStateOf(targetYearOptions[0]) }

    if (firebaseAuth.isUserAlreadyLoggedIn()) {
        onSignUpSuccess()
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 50.dp, start = 20.dp, end = 20.dp, bottom = 10.dp),
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
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFFe83337),
                unfocusedBorderColor = MaterialTheme.colorScheme.tertiaryContainer,
                focusedTextColor = Color.Black,
                focusedLabelColor = Color.Black
            )
        )

        // Password Field
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFFe83337),
                unfocusedBorderColor = MaterialTheme.colorScheme.tertiaryContainer,
                focusedTextColor = Color.Black,
                focusedLabelColor = Color.Black
            )
        )

        // Phone Number Field (with country code +91)
        OutlinedTextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            label = { Text("Phone Number") },
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.Phone, contentDescription = null)
                    Text("+91", color = Color.Black)
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFFe83337),
                unfocusedBorderColor = MaterialTheme.colorScheme.tertiaryContainer,
                focusedTextColor = Color.Black,
                focusedLabelColor = Color.Black
            )
        )

        // Dropdown for selecting class
        OutlinedTextField(
            value = selectedClass,
            onValueChange = { selectedClass = it },
            label = { Text("Select Class") },
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = { Icon(Icons.Filled.ArrowDropDown, contentDescription = "Dropdown Icon") },
            readOnly = true, // This makes it look like a dropdown
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFFe83337),
                unfocusedBorderColor = MaterialTheme.colorScheme.tertiaryContainer,
                focusedTextColor = Color.Black,
                focusedLabelColor = Color.Black
            )
        )

        // Row for entrance exam and target year dropdowns
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Entrance Exam Dropdown
            OutlinedTextField(
                value = selectedExam,
                onValueChange = { selectedExam = it },
                label = { Text("Select Exam") },
                modifier = Modifier.weight(1f),
                trailingIcon = { Icon(Icons.Filled.ArrowDropDown, contentDescription = "Dropdown Icon") },
                readOnly = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xFFe83337),
                    unfocusedBorderColor = MaterialTheme.colorScheme.tertiaryContainer,
                    focusedTextColor = Color.Black,
                    focusedLabelColor = Color.Black
                )
            )

            // Target Year Dropdown
            OutlinedTextField(
                value = selectedTargetYear.toString(),
                onValueChange = { /* Do nothing, as it's a read-only field for now */ },
                label = { Text("Target Year") },
                modifier = Modifier.weight(1f),
                trailingIcon = { Icon(Icons.Filled.ArrowDropDown, contentDescription = "Dropdown Icon") },
                readOnly = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xFFe83337),
                    unfocusedBorderColor = MaterialTheme.colorScheme.tertiaryContainer,
                    focusedTextColor = Color.Black,
                    focusedLabelColor = Color.Black
                )
            )
        }

        // Sign-Up Button
        Button(
            onClick = {
                firebaseAuth.signUp(email, password, context) { task ->
                    if (task.isSuccessful) {
                        onSignUpSuccess()
                    } else {
                        signUpError = task.exception?.message
                        Toast.makeText(context, signUpError, Toast.LENGTH_SHORT).show()
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .padding(28.dp)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFe83337))
        ) {
            Text("Sign Up", color = Color.White, fontWeight = FontWeight.ExtraBold, fontSize = 20.sp)
        }

        signUpError?.let {
            Text("Error: $it", color = Color.Red)
        }
    }
}
