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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nabssam.bestbook.presentation.ui.book.bookList.composable.CategoryChipList
import com.nabssam.bestbook.presentation.ui.components.BookCoverImage
import com.nabssam.bestbook.presentation.ui.components.BookTitlePrice
import com.nabssam.bestbook.presentation.ui.components.ErrorScreen
import com.nabssam.bestbook.presentation.ui.components.FullScreenProgressIndicator

@Composable
fun BookListScreen(
    modifier: Modifier = Modifier.fillMaxSize(),
    state: StateBookList,
    goToDetail: (String, String) -> Unit,
    onEvent: (EventBookList) -> Unit,
) {
    if (state.fetchingBooks) {
        FullScreenProgressIndicator(modifier = modifier, message = "Loading...")
    } else if (state.errorMessage != null) {
        ErrorScreen(
            message = state.errorMessage,
            modifier = modifier,
            onRetry = { onEvent(EventBookList.Retry) }
        )
    } else {
        Column {
            CategoryChipList(
                examList = state.categories,
                onClick = { onEvent(EventBookList.SortBy(it)) }
            )
            HorizontalDivider()
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = modifier
                    .fillMaxSize()
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(state.fetchedBooks) {
                    Box(
                        modifier = Modifier
                            .clickable { goToDetail(it.id, it.name) },
                        contentAlignment = Alignment.TopCenter
                    ) {
                        Column {
                            BookCoverImage(coverImage = it.coverUrl)
                            //book title and price
                            BookTitlePrice(
                                maxLine = 2,
                                discPer = it.hardCopyDis,
                                originalPrice = it.price,
                                title = it.name,
//                                rating = it.rate.points
                            )
                        }
                    }

                }
            }
        }
    }
}

