package com.imageclasses.imageclasses.ui.feature.account.auth

import android.widget.Toast
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.imageclasses.imageclasses.auth.FirebaseAuth
import com.imageclasses.imageclasses.controllers.RealtimeDb
import com.imageclasses.imageclasses.navigation.DestinationScreen
import java.util.*




    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun SignUp(modifier: Modifier = Modifier, navController: NavController) {
        val db = RealtimeDb()
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
        val targetYearOptions =
            (currentYear..currentYear + 5).toList() // Example target years (next 5 years)
        var selectedTargetYear by remember { mutableStateOf(targetYearOptions[0]) }
        var expandedClass by remember { mutableStateOf(false) }
        var expandedExam by remember { mutableStateOf(false) }
        var expandedTargetYear by remember { mutableStateOf(false) }




        Column(
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding( start = 20.dp, end = 20.dp, bottom = 10.dp),
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
                    focusedLabelColor = Color.Black,
                    unfocusedTextColor = Color.Black
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
                    focusedLabelColor = Color.Black,
                    unfocusedTextColor = Color.Black
                )
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
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xFFe83337),
                    unfocusedBorderColor = MaterialTheme.colorScheme.tertiaryContainer,
                    focusedTextColor = Color.Black,
                    focusedLabelColor = Color.Black,
                    unfocusedTextColor = Color.Black
                )
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
                    colors = TextFieldDefaults.outlinedTextFieldColors(
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
                        colors = TextFieldDefaults.outlinedTextFieldColors(
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
                        colors = TextFieldDefaults.outlinedTextFieldColors(
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
            if (email.isNotEmpty() && password.isNotEmpty() && phoneNumber.isNotEmpty() && selectedClass.isNotEmpty() && selectedExam.isNotEmpty() && selectedTargetYear.toString()
                    .isNotEmpty()
            )
                isDisable = false
            else
                isDisable = true
            Button(
                onClick = {
                    (
                            firebaseAuth.signUp(email, password, context) { task ->
                                if (task.isSuccessful) {
                                    db.writeData(
                                        email = email,
                                        phoneNumber = phoneNumber,
                                        classname = selectedClass,
                                        targetYear = selectedTargetYear.toString(),
                                        entranceExam = selectedExam
                                    ){
                                        success->
                                        if(success){
                                            navController.navigate(DestinationScreen.home.route)
                                        }
                                        else{
                                            navController.navigate(DestinationScreen.auth_signin.route)
                                        }
                                    }

                                } else {
                                    signUpError = task.exception?.message
                                    Toast.makeText(context, signUpError, Toast.LENGTH_SHORT).show()
                                }
                            })

                },
                enabled = !isDisable,

                modifier = Modifier

                    .fillMaxWidth(0.7f)
                    .padding(28.dp)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFe83337), disabledContainerColor = Color.DarkGray)
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
        }
    }

