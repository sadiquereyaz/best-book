package com.nabssam.bestbook.presentation.ui.account.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.nabssam.bestbook.R
import com.nabssam.bestbook.presentation.navigation.Route
import java.util.Calendar

@Composable
fun SignUp(modifier: Modifier = Modifier, navController: NavController) {
    //val db = RealtimeDb()
//    val firebaseAuth = FirebaseAuth()
//    val context = LocalContext.current
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
    val targetYearOptions =
        (currentYear..currentYear + 5).toList() // Example target years (next 5 years)
    var selectedTargetYear by remember { mutableStateOf(targetYearOptions[0]) }
    var expandedClass by remember { mutableStateOf(false) }
    var expandedExam by remember { mutableStateOf(false) }
    var expandedTargetYear by remember { mutableStateOf(false) }
    var passwordVisible by remember { mutableStateOf(false) }




    Column(
        modifier = modifier
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

        // Password Field
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            isError = password.length < 6 && password.isNotEmpty(),
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = (if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation()),

            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            colors = OutlinedTextFieldDefaults.colors(
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

        // Phone Number Field (with country code +91)
        OutlinedTextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            label = { Text("Phone Number") },
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(start = 10.dp, end = 2.dp),
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Phone,
                        contentDescription = null,
                        tint = Color.Black
                    )
                    Text("|", color = Color.Gray, fontSize = 28.sp)
                    Text("+91", color = Color.Black)
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            colors =  OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFFe83337),
                unfocusedBorderColor = MaterialTheme.colorScheme.tertiaryContainer,
                focusedTextColor = Color.Black,
                focusedLabelColor = Color.Black,
                unfocusedTextColor = Color.Black
            ),
            isError = phoneNumber.length < 10 && phoneNumber.length > 0,
            supportingText = {
                if (phoneNumber.length < 10 && phoneNumber.length > 0) {
                    Text("Phone number must be 10 digits", color = Color.DarkGray)
                }
            }
        )

        // Dropdown for selecting class
        Box {
            OutlinedTextField(
                value = selectedClass,
                onValueChange = { }, // Do nothing, as it's a read-only field
                label = { Text("Select Class") },
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    IconButton(onClick = { expandedClass = !expandedClass }) {
                        Icon(
                            Icons.Filled.ArrowDropDown,
                            contentDescription = "Dropdown Icon"
                        )
                    }
                },
                readOnly = true,
                colors =  OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFe83337),
                    unfocusedBorderColor = MaterialTheme.colorScheme.tertiaryContainer,
                    focusedTextColor = Color.Black,
                    focusedLabelColor = Color.Black,
                    unfocusedTextColor = Color.Black
                )
            )
            DropdownMenu(
                expanded = expandedClass,
                onDismissRequest = { expandedClass = false }
            ) {
                classOptions.forEach { selectionOption ->
                    DropdownMenuItem(
                        onClick = {
                            selectedClass = selectionOption
                            expandedClass = false
                        },
                        text = { Text(selectionOption) }
                    )
                }
            }
        }

        // Row for entrance exam and target year dropdowns
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Entrance Exam Dropdown
            Box {
                OutlinedTextField(
                    value = selectedExam,
                    onValueChange = { selectedExam = it },
                    label = { Text("Select Exam") },
                    modifier = Modifier.fillMaxWidth(0.6f),
                    trailingIcon = {
                        IconButton(onClick = { expandedExam = !expandedExam }) {
                            Icon(
                                Icons.Filled.ArrowDropDown,
                                contentDescription = "Dropdown Icon"
                            )
                        }
                    },
                    readOnly = true,
                    colors =  OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFFe83337),
                        unfocusedBorderColor = MaterialTheme.colorScheme.tertiaryContainer,
                        focusedTextColor = Color.Black,
                        focusedLabelColor = Color.Black,
                        unfocusedTextColor = Color.Black
                    )
                )


                DropdownMenu(
                    expanded = expandedExam,
                    onDismissRequest = { expandedExam = false }
                ) {
                    entranceExamOptions.forEach { selectionOption ->
                        DropdownMenuItem(
                            onClick = {
                                selectedExam = selectionOption
                                expandedExam = false
                            },
                            text = { Text(selectionOption) }
                        )
                    }
                }
            }

            // Target Year Dropdown
            Box {
                OutlinedTextField(
                    value = selectedTargetYear.toString(),
                    onValueChange = { /* Do nothing, as it's a read-only field for now */ },
                    label = { Text("Target Year") },
                    modifier = Modifier.fillMaxWidth(),
                    trailingIcon = {
                        IconButton(onClick = { expandedTargetYear = !expandedTargetYear }) {
                            Icon(
                                Icons.Filled.ArrowDropDown,
                                contentDescription = "Dropdown Icon"
                            )
                        }
                    },
                    readOnly = true,
                    colors =  OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFFe83337),
                        unfocusedBorderColor = MaterialTheme.colorScheme.tertiaryContainer,
                        focusedTextColor = Color.Black,
                        focusedLabelColor = Color.Black,
                        unfocusedTextColor = Color.Black
                    )
                )

                DropdownMenu(
                    expanded = expandedTargetYear,
                    onDismissRequest = { expandedTargetYear = false }
                ) {
                    targetYearOptions.forEach { selectionOption ->
                        DropdownMenuItem(
                            onClick = {
                                selectedTargetYear = selectionOption
                                expandedTargetYear = false
                            },
                            text = { Text(selectionOption.toString()) }
                        )
                    }
                }
            }
        }

        // Sign-Up Button
        var isDisable by remember { mutableStateOf(false) }
        if (email.isNotEmpty() && password.length >= 6 && phoneNumber.length == 10 && selectedClass.isNotEmpty() && selectedExam.isNotEmpty() && selectedTargetYear.toString()
                .isNotEmpty()
        )
            isDisable = false
        else
            isDisable = true
        Column(
            verticalArrangement = Arrangement.spacedBy(2.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {

                           /* firebaseAuth.signUp(email, password, context) { task ->
                                if (task.isSuccessful) {
                                    db.writeData(
                                        email = email,
                                        phoneNumber = phoneNumber,
                                        classname = selectedClass,
                                        targetYear = selectedTargetYear.toString(),
                                        entranceExam = selectedExam
                                    ) { success ->
                                        if (success) {
                                            navController.navigate(Route.MainGraph)
                                        } else {
                                            //navController.navigate(Route.Signin)
                                        }
                                    }

                                } else {
                                    signUpError = task.exception?.message
                                    Toast.makeText(context, signUpError, Toast.LENGTH_SHORT).show()
                                }
                            }*/


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
                    "Sign Up",
                    color = Color.White,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 20.sp
                )
            }

            signUpError?.let {
                Text("Error: $it", color = Color.Red)
            }

            TextButton(
                onClick = {
                    navController.navigate(Route.SignIn())
                }
            ) {
                Text(
                    text = "Already have an account? Sign in"
                )
            }
        }
    }
}

