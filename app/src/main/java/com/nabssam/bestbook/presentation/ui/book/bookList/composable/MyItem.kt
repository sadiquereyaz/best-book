package com.nabssam.bestbook.presentation.ui.book.bookList.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

data class MyItem(val id: Int, val title: String)

@Composable
fun MyList(items: List<MyItem>) {
    var selectedItemId by remember { mutableIntStateOf(-1) }

    LazyColumn {
        items(items) { item ->
            ListItem(
                item = item,
                isSelected = selectedItemId == item.id
            ) {
                selectedItemId = if (selectedItemId == item.id) -1 else item.id
            }
        }
    }
}

@Composable
fun ListItem(item: MyItem, isSelected: Boolean, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Text(
            text = item.title,
            color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Unspecified
        )
    }
}

// Define your text styles (selected and default) here