package com.nabssam.bestbook.presentation.ui.book.bookList

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.nabssam.bestbook.presentation.ui.book.bookList.composable.FilterBottomSheet
import com.nabssam.bestbook.presentation.ui.book.bookList.composable.SearchAndFilter
import com.nabssam.bestbook.presentation.ui.components.BookCoverImage
import com.nabssam.bestbook.presentation.ui.components.BookTitlePrice
import com.nabssam.bestbook.presentation.ui.components.ErrorScreen
import com.nabssam.bestbook.presentation.ui.components.TranslucentLoader
import kotlin.math.absoluteValue

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
        TranslucentLoader(modifier = modifier, message = "Loading...")
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
            SearchAndFilter(
                state,
                onEvent,
                focusManager,
                showFilterSheet = { showFilterSheet = it })

            HorizontalDivider(
                modifier = if (state.selectedCategories.isEmpty())
                    Modifier.padding(top = 12.dp) else Modifier
            )

            if (state.filteredBooks.isEmpty())
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No books found. Try adjusting your filters.",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            else
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
                                book.coverUrl?.let { imageUrl ->
                                    BookCoverImage(
                                        rate = book.averageRate ?: 0.0,
                                        coverImageUrl = imageUrl,
                                        onClick = { goToDetail(book.id, book.name) }
                                    )
                                }
                                BookTitlePrice(
                                    maxLine = 2,
                                    discPer = book.hardCopyDis ?: 0,
                                    originalPrice = book.price,
                                    title = book.name
                                )
                            }
                        }
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