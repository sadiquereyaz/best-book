package com.nabssam.bestbook.presentation.ui.book.bookList.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarComposable(
    modifier: Modifier = Modifier,
    books: List<String>,
    onBookSelected: (String) -> Unit
) {
    var query by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    // Filtered book list based on query
    val filteredBooks = books.filter { it.contains(query, ignoreCase = true) }
    val textFieldState = rememberTextFieldState()
    val scope = rememberCoroutineScope()

    val inputField =
        @Composable {
            SearchBarDefaults.InputField(
                modifier = Modifier
                    .clip(shape = CircleShape)
                    .background(MaterialTheme.colorScheme.background)
                    .height(TextFieldDefaults.MinHeight.times(0.9f)),
//                searchBarState = searchBarState,
//                textFieldState = textFieldState,
//                onSearch = { scope.launch { searchBarState.animateToCollapsed() } },
                placeholder = { Text("Search...") },
                leadingIcon = {
                    if (expanded) {
                        IconButton(
                            onClick = { scope.launch { expanded = false } }
                        ) {
                            Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = "Back")
                        }
                    } else {
                        Icon(Icons.Default.Search, contentDescription = null)
                    }
                },
                trailingIcon = {
                    if (query.isNotBlank())
                        IconButton(onClick = { query = "" }) {
                            Icon(Icons.Default.Clear, contentDescription = null)
                        }
                },
                query = query,
                onQueryChange = { query = it },
                onSearch = { expanded = true },
                expanded = false,
                onExpandedChange = { expanded = it },
                //placeholder = { Text("Search books...") },
            )
        }

    SearchBar(
        inputField = inputField
        /*{
            SearchBarDefaults.InputField(
                query = query,
                onQueryChange = { query = it },
                onSearch = { expanded = true },
                expanded = false,
                onExpandedChange = { expanded = it },
                placeholder = { Text("Search books...") },
            )
            TextField(
                value = query,
                onValueChange = { query = it },
                modifier = modifier.fillMaxWidth(),
                placeholder = { Text("Search books...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                trailingIcon = {
                    if (query.isNotEmpty()) {
                        IconButton(onClick = { query = "" }) {
                            Icon(Icons.Default.Clear, contentDescription = "Clear")
                        }
                    }
                }
            )

        }*/,
        expanded = expanded,
        onExpandedChange = { expanded = it },
        modifier = modifier
            .fillMaxWidth(),
        shape = CircleShape,
        colors = SearchBarDefaults.colors(),
        tonalElevation = SearchBarDefaults.TonalElevation,
        shadowElevation = SearchBarDefaults.ShadowElevation,
        windowInsets = WindowInsets(0, 0, 0, 0)
    ) {
        LazyColumn {
            items(filteredBooks) { book ->
                ListItem(
                    headlineContent = { Text(book) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onBookSelected(book)
                            expanded = false
                        }
                        .padding(16.dp)
                )
            }
        }
    }
}

