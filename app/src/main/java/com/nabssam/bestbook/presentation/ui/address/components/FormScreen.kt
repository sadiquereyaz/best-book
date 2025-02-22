package com.nabssam.bestbook.presentation.ui.address.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.nabssam.bestbook.presentation.ui.address.FormField
import com.nabssam.bestbook.presentation.ui.address.UiStateAddress
import com.nabssam.bestbook.presentation.ui.components.DropDownComposable

@Composable
fun FormScreen(
    modifier: Modifier = Modifier,
    formState: UiStateAddress.Success.Form,
    onFieldUpdated: (FormField, String) -> Unit,
    onSubmit: () -> Unit
) {
    val indianStates = listOf(
        "Andaman and Nicobar Islands",
        "Andhra Pradesh",
        "Arunachal Pradesh",
        "Assam",
        "Bihar",
        "Chandigarh",
        "Chhattisgarh",
        "Dadra and Nagar Haveli and Daman and Diu",
        "Delhi",
        "Goa",
        "Gujarat",
        "Haryana",
        "Himachal Pradesh",
        "Jharkhand",
        "Karnataka",
        "Kerala",
        "Ladakh",
        "Lakshadweep",
        "Madhya Pradesh",
        "Maharashtra",
        "Manipur",
        "Meghalaya",
        "Mizoram",
        "Nagaland",
        "Odisha",
        "Puducherry",
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
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = formState.firstName,
                onValueChange = { onFieldUpdated(FormField.FirstName, it) },
                label = { Text("First Name") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    capitalization = KeyboardCapitalization.Words,
                    imeAction = ImeAction.Next
                ),
                singleLine = true
            )
            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = formState.lastName,
                onValueChange = { onFieldUpdated(FormField.LastName, it) },
                label = { Text("Last Name") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    capitalization = KeyboardCapitalization.Words,
                    imeAction = ImeAction.Next
                ),
                singleLine = true
            )
        }
        //pin-code
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = formState.pinCode,
            onValueChange = { onFieldUpdated(FormField.Pincode, it) },
            label = { Text("Pin code") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            trailingIcon = {
                TextButton(onClick = {}) {
                    Text(text = "Validate")
                }
            },
            singleLine = true,
            // supportingText = { Text("Item deliverable") }
        )
        //pin-code
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = formState.phone,
            onValueChange = { onFieldUpdated(FormField.Phone, it) },
            label = { Text("Mobile number") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            trailingIcon = {
                TextButton(onClick = {}) {
                    Text(text = "Validate")
                }
            },
            singleLine = true,
            // supportingText = { Text("Item deliverable") }
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = formState.street,
            onValueChange = { onFieldUpdated(FormField.Street, it) },
            label = { Text("Street") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            ),
            singleLine = true
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = formState.locality,
            onValueChange = { onFieldUpdated(FormField.Locality, it) },
            label = { Text("Locality") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            ),
            singleLine = true
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = formState.city,
            onValueChange = { onFieldUpdated(FormField.City, it) },
            label = { Text("City") },
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