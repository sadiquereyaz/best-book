package com.nabssam.bestbook.presentation.ui.address

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.nabssam.bestbook.PhonePeActivity
import com.nabssam.bestbook.R
import com.nabssam.bestbook.presentation.ui.address.components.ErrorView
import com.nabssam.bestbook.presentation.ui.components.DropDownComposable

@Composable
fun AddressScreen(
    modifier: Modifier = Modifier,
    goToPayment: () -> Unit,
    uiState: UiStateAddress,
    onEvent: (EventAddressScreen) -> Unit,
) {
    Box(modifier = modifier.fillMaxSize()) {
        when (uiState) {
            is UiStateAddress.Error -> {
                ErrorView(
                    message = uiState.message,
                    onRetry = { onEvent(EventAddressScreen.LoadAddress) }
                )
            }

            is UiStateAddress.Loading -> CircularProgressIndicator(
                modifier = Modifier.align(
                    Alignment.Center
                )
            )

            is UiStateAddress.Success -> {
                val context = LocalContext.current
                var fieldVisible by rememberSaveable { mutableStateOf(false) }
                var fullName by rememberSaveable { mutableStateOf("") }
                var mobileNum by rememberSaveable { mutableStateOf("") }
                var address by rememberSaveable { mutableStateOf("") }
                var city by rememberSaveable { mutableStateOf("") }
                var province by rememberSaveable { mutableStateOf("") }
                val entranceExamOptions = listOf("Lucknow", "Aligarh", "Allahabad")
                val radioOptions = listOf(
                    "Block 3, FRK Boys Hostel, Jamia Millia Islamia, 220123, New Delhi, Delhi",
                    "Block 21, New Boys Hostel, Jamia Millia Islamia, 220123, New Delhi, Delhi",
                )

                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    // add address btn
                    TextButton(onClick = {
                        fieldVisible = !fieldVisible
                        onEvent(EventAddressScreen.ToggleAddressFields(fieldVisible))
                    }) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = ImageVector.vectorResource(
                                    id = if (fieldVisible) R.drawable.history else R.drawable.add_home
                                ),
                                "address icon"
                            )
                            Text(text = if (fieldVisible) "Show Saved Address" else "Add New Address")
                        }
                    }

                    when (uiState) {

                        is UiStateAddress.Success.Addresses -> {
                            val (selectedOption1, onOptionSelected1) = remember {
                                mutableStateOf(
                                    uiState.addresses[0]
                                )
                            }
                            uiState.addresses.forEach { address ->
                                AddressRadio(
                                    Modifier.fillMaxWidth(),
                                    address,
                                    selectedOption1,
                                    onOptionSelected1
                                )
                            }
                            Button(
                                onClick = {
                                    val intent =
                                        Intent(context, PhonePeActivity::class.java).apply {
                                            putExtra("pdf_path", "pdfFile.absolutePath")
                                        }
                                    context.startActivity(intent)
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(18.dp)
                            ) {
                                Text("Checkout")
                            }
                        }

                        is UiStateAddress.Success.Form -> {
                            FormScreen(
                                modifier = Modifier.fillMaxWidth(),
                                formState = uiState,
                                onFieldUpdated = { field, value ->
                                    onEvent(EventAddressScreen.UpdateFormField(field, value))
                                },
                                onSubmit = {
                                    onEvent(EventAddressScreen.SubmitForm)
                                }
                            )
                        }
                    }


                    /*    if (fieldVisible) {
                            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))
                            TextField(
                                value = mobileNum,
                                onValueChange = { mobileNum = it },
                                label = { Text("Phone") },
                                singleLine = true,
                                textStyle = TextStyle(color = Color.Blue, fontWeight = FontWeight.Bold),
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth(),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
                            )
                            TextField(
                                value = address,
                                onValueChange = { address = it },
                                label = { Text("Address") },
                                maxLines = 5,
                                minLines = 5,
                                textStyle = TextStyle(fontWeight = FontWeight.Normal),
                                modifier = Modifier
                                    .padding(horizontal = 16.dp)
                                    .fillMaxWidth()
                            )
                            DropDownComposable(
                                modifier = Modifier
                                    .padding(horizontal = 16.dp, vertical = 8.dp)
                                    .fillMaxWidth(),
                                options = entranceExamOptions,
                                label = "State",
                                onOptionSelected = { province = it }
                            )
                        }*/


                }
            }
        }
    }
}

@Composable
private fun AddressRadio(
    modifier: Modifier = Modifier,
    text: String,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .selectable(
                selected = (text == selectedOption),
                onClick = { onOptionSelected(text) },
                role = Role.RadioButton
            )
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = (text == selectedOption),
            onClick = null
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

@Composable
fun FormScreen(
    modifier: Modifier = Modifier,
    formState: UiStateAddress.Success.Form,
    onFieldUpdated: (FormField, String) -> Unit,
    onSubmit: () -> Unit
) {
    val indianStates = listOf(
        "Andhra Pradesh",
        "Arunachal Pradesh",
        "Assam",
        "Bihar",
        "Chhattisgarh",
        "Goa",
        "Gujarat",
        "Haryana",
        "Himachal Pradesh",
        "Jharkhand",
        "Karnataka",
        "Kerala",
        "Madhya Pradesh",
        "Maharashtra",
        "Manipur",
        "Meghalaya",
        "Mizoram",
        "Nagaland",
        "Odisha",
        "Punjab",
        "Rajasthan",
        "Sikkim",
        "Tamil Nadu",
        "Telangana",
        "Tripura",
        "Uttar Pradesh",
        "Uttarakhand",
        "West Bengal"
    )
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = formState.name,
            onValueChange = { onFieldUpdated(FormField.Name, it) },
            label = { Text("Full Name") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            ),
            singleLine = true
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = formState.pinCode,
            onValueChange = { onFieldUpdated(FormField.Pincode, it) },
            label = { Text("Pin code") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            singleLine = true
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = formState.post,
            onValueChange = { onFieldUpdated(FormField.Post, it) },
            label = { Text("Post") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            ),
            singleLine = true
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = formState.district,
            onValueChange = { onFieldUpdated(FormField.District, it) },
            label = { Text("District") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            ),
            singleLine = true
        )
        DropDownComposable(
            modifier = Modifier
                .fillMaxWidth(),
            options = indianStates,
            label = "State",
            onOptionSelected = { onFieldUpdated(FormField.State, it) }
        )
        Button(
            onClick = onSubmit,
            enabled = formState.isSubmitEnabled,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Address")
        }
    }
}

