package com.nabssam.bestbook.presentation.ui.book.bookList

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.nabssam.bestbook.R
import com.nabssam.bestbook.presentation.ui.book.bookList.composable.EnhancedElevatedChip
import com.nabssam.bestbook.presentation.ui.book.bookList.composable.EnhancedSearchBar
import com.nabssam.bestbook.presentation.ui.book.bookList.composable.FilterBottomSheet
import com.nabssam.bestbook.presentation.ui.components.BookCoverImage
import com.nabssam.bestbook.presentation.ui.components.BookTitlePrice
import com.nabssam.bestbook.presentation.ui.components.ErrorScreen
import com.nabssam.bestbook.presentation.ui.components.FullScreenProgressIndicator

@Composable
fun BookListScreen(
    modifier: Modifier = Modifier,
    state: StateBookList,
    goToDetail: (String, String) -> Unit,
    onEvent: (EventBookList) -> Unit,
) {
    var showFilterSheet by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current


    if (state.fetchingBooks) {
        FullScreenProgressIndicator(modifier = modifier, message = "Loading...")
    } else if (state.errorMessage != null) {
        ErrorScreen(
            message = state.errorMessage,
            modifier = modifier,
            onRetry = { onEvent(EventBookList.Retry) }
        )
    } else {

        Column(
            modifier = modifier.fillMaxSize()
//                .background(MaterialTheme.colorScheme.surface)
        ) {
            SearchFilter(state, onEvent, focusManager, showFilterSheet = { showFilterSheet = it })

            HorizontalDivider(
                modifier = if (state.selectedCategories.isEmpty())
                    Modifier.padding(top = 12.dp) else Modifier
            )

            // Book grid
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp, 12.dp, 12.dp, 0.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(state.filteredBooks) { book ->
                    Box(
                        modifier = Modifier
                            .clickable { goToDetail(book.id, book.name) },
                        contentAlignment = Alignment.TopCenter
                    ) {
                        Column {
                            BookCoverImage(
                                coverImage = book.coverUrl,
                                onClick = { goToDetail(book.id, book.name) }
                            )
                            BookTitlePrice(
                                maxLine = 2,
                                discPer = book.hardCopyDis,
                                originalPrice = book.price,
                                title = book.name
                            )
                        }
                    }
                }
            }

            // Empty state
            if (state.filteredBooks.isEmpty() && !state.fetchingBooks) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No books found. Try adjusting your filters.",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }

        // Filter bottom sheet
        if (showFilterSheet) {
            FilterBottomSheet(
                state = state,
                onEvent = onEvent,
                onDismiss = { showFilterSheet = false }
            )
        }
    }
}

@Composable
fun ColumnScope.SearchFilter(
    state: StateBookList,
    onEvent: (EventBookList) -> Unit,
    focusManager: FocusManager,
    showFilterSheet: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp, 0.dp, 12.dp, 0.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Enhanced SearchBar
        EnhancedSearchBar(
            modifier = Modifier.weight(1f),
            query = state.searchQuery,
            onQueryChange = { onEvent(EventBookList.UpdateSearchQuery(it)) },
            focusManager = focusManager
        )

        // Filter button
        IconButton(
            modifier = Modifier
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceContainer)
                .size(TextFieldDefaults.MinHeight),
            onClick = {
                focusManager.clearFocus()
                showFilterSheet(true)
            }
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.sort_icon),
                contentDescription = "Filter books"
            )
        }
    }

    // Selected category chips
    AnimatedVisibility(
        visible = state.selectedCategories.isNotEmpty(),
        enter = fadeIn(animationSpec = tween(500)) + slideInVertically(),
        exit = fadeOut(animationSpec = tween(500)) + slideOutVertically()
    ) {
        EnhancedElevatedChip(
            modifier = Modifier.padding(16.dp, 0.dp),
            examList = state.selectedCategories.toList(),
            onClick = { category ->
                onEvent(EventBookList.ToggleCategory(category))
            }
        )
        //FilterChip() { }
    }
}
