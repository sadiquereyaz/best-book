package com.nabssam.bestbook.presentation.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun OfflineDialog(
    isVisible: Boolean,
    onRetryClick: () -> Unit
) {
    if (isVisible) {
        AlertDialog(
            modifier = Modifier.padding(32.dp),
            onDismissRequest = { /* Prevent dismissing */ },
            confirmButton = {
                TextButton(onClick = onRetryClick) {
                    Text("Retry")
                }
            },
            dismissButton = {},
            title = { Text("No Internet Connection") },
            text = {
                Text(
                    "You are not connected to the internet. Please connect to the network."
                )
            }
        )
    }
}
