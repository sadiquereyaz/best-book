package com.nabssam.bestbook.presentation.ui.address.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.nabssam.bestbook.data.remote.dto.Address

@Composable
 fun AddressRadio(
    modifier: Modifier = Modifier,
    address: Address,
    selectedOption: String?,
    onOptionSelected: (String) -> Unit,
    deleteAddress: ()->Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp, 8.dp)
            .clickable { onOptionSelected(address._id) }, // Make the entire card clickable
        colors = CardDefaults.cardColors(
            containerColor = if (address._id == selectedOption) MaterialTheme.colorScheme.primaryContainer else CardDefaults.cardColors().containerColor // Highlight selected card
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp) // Add elevation for a card look
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .selectable(
                    selected = (address._id == selectedOption),
                    onClick = { onOptionSelected(address._id) },
                    role = Role.RadioButton
                )
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = (address._id == selectedOption),
                onClick = null
            )
            Text(
                text ="${address.firstName} ${address.lastName}, ${address.phone}, ${address.pincode}, ${address.address1},${address.address2}, ${address.state}, ${address.country}"
                ,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .weight(1f)
            )
            IconButton(
                onClick = {deleteAddress()}
            ) {
                Icon(Icons.Default.Delete, "delete", tint = MaterialTheme.colorScheme.error)
            }

        }
    }
}