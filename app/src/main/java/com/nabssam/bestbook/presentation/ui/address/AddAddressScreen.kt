package com.nabssam.bestbook.presentation.ui.address

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.nabssam.bestbook.presentation.navigation.Route

@Composable
fun AddressScreen(
    modifier: Modifier = Modifier,
    goToPayment: () -> Unit,
) {
    var addNewAddress by rememberSaveable { mutableStateOf(false) }
    var fullName by rememberSaveable { mutableStateOf("") }
    var mobileNum by rememberSaveable { mutableStateOf("") }
    var address by rememberSaveable { mutableStateOf("") }
    var city by rememberSaveable { mutableStateOf("") }
    var state by rememberSaveable { mutableStateOf("") }
    var expandedTargetYear by remember { mutableStateOf(false) }
    val entranceExamOptions = listOf("Lucknow", "Aligarh", "Allahabad") // Example entrance exams

    val radioOptions = listOf(
        "Block 3, FRK Boys Hostel, Jamia Millia Islamia, 220123, New Delhi, Delhi",
        "Block 21, New Boys Hostel, Jamia Millia Islamia, 220123, New Delhi, Delhi",
    )
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        TextButton(
            onClick = { addNewAddress = !addNewAddress }
        ) {
            Row { Icon(Icons.Filled.Add, "add address") }
            Text("Add New Address")
        }
        // Note that Modifier.selectableGroup() is essential to ensure correct accessibility behavior
        Column(Modifier.selectableGroup()) {
            radioOptions.forEach { text ->
                Row(
                    Modifier
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
                        onClick = null // null recommended for accessibility with screen readers
                    )
                    Text(
                        text = text,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }
        }



        if (addNewAddress) {
            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))
            TextField(
                value = fullName,
                onValueChange = { fullName = it },
                label = { Text("Full Name") },
                maxLines = 1,
                textStyle = TextStyle(fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                //placeholder = { Text("") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    capitalization = KeyboardCapitalization.Words
                )
            )
            TextField(
                value = mobileNum,
                onValueChange = { mobileNum = it },
                label = { Text("Phone") },
                singleLine = true,
                textStyle = TextStyle(color = Color.Blue, fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone,
                )
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
                    .fillMaxWidth(),
                // placeholder = { Text("Gali 21") }
            )
            DropdownMenu(
                expanded = expandedTargetYear,
                onDismissRequest = { expandedTargetYear = false },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .width(120.dp)
                    .height(40.dp)
            ) {
                entranceExamOptions.forEach { selectionOption ->
                    DropdownMenuItem(
                        onClick = {
                            city = selectionOption
                            expandedTargetYear = false
                        },
                        text = { Text(selectionOption.toString()) }
                    )
                }
            }

        }
        Button(
            onClick = goToPayment,
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
        ) {
            Text("Deliver here")
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun Preview(
    navController: NavController = rememberNavController()

) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Image Classes") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
            )
        },
        /* bottomBar = {
             BottomNavigationMenu(
                 navController = rememberNavController()
             )
         }*/
    ) { it ->
        AddressScreen(Modifier.padding(paddingValues = it)) {
            navController.navigate(Route.OrderRoute())
        }
    }
}

