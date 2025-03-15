package com.nabssam.bestbook.presentation.ui.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreenRowItem(
    modifier: Modifier = Modifier,
    title: String,
    btnText: String = "View all",
    onClick: ()-> Unit,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.weight(1f))
            TextButton(
                onClick = { onClick() },
                contentPadding = PaddingValues(0.dp),
            ) {
                Text(
                    text = btnText,
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
        content()
    }
}