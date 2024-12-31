package com.nabssam.bestbook.ui.feature.orderConfirmScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Composable
fun OrderScreen(
    modifier: Modifier = Modifier,
) {
    var selectedTabIndex by remember { mutableStateOf(0) }

    Column(modifier = modifier) { // Use a Column to organize content
        TabRow(selectedTabIndex = selectedTabIndex) {
            Tab(
                text = { Text("Orders") },
                selected = selectedTabIndex == 0,
                onClick = { selectedTabIndex = 0 }
            )
            Tab(
                text = { Text("History") },
                selected = selectedTabIndex == 1,
                onClick = { selectedTabIndex = 1 }
            )
        }

        when (selectedTabIndex) {
            0 -> {
                Text("Orders content") // Replace with actual content
            }
            1 -> {
                Text("History content") // Replace with actual content
            }
        }
    }
}

@Composable
fun PaymentStatusDialog(
    modifier: Modifier = Modifier
){

}