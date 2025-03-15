package com.nabssam.bestbook.presentation.ui.book.bookList

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.nabssam.bestbook.R
import com.nabssam.bestbook.presentation.ui.book.bookList.composable.CategoryChipList
import com.nabssam.bestbook.presentation.ui.book.bookList.composable.SearchBarComposable
import com.nabssam.bestbook.presentation.ui.components.BookCoverImage
import com.nabssam.bestbook.presentation.ui.components.BookTitlePrice
import com.nabssam.bestbook.presentation.ui.components.ErrorScreen
import com.nabssam.bestbook.presentation.ui.components.FullScreenProgressIndicator

@OptIn(ExperimentalMaterial3Api::class)
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
        val books = listOf(
            "The Great Gatsby", "Moby Dick", "Pride and Prejudice",
            "To Kill a Mockingbird", "1984", "Harry Potter"
        )

        var selectedBook by remember { mutableStateOf("") }

        Column(
            modifier = modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
                    .wrapContentWidth()
                ,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
            SearchBarComposable(
                modifier = Modifier
                        .weight(1f)
                ,
                books = books,
                onBookSelected = { selectedBook = it }
            )
                //FilterIconButton(onclick = {})
            }

            CategoryChipList(
                examList = state.categories,
                onClick = { onEvent(EventBookList.SortBy(it)) }
            )
            HorizontalDivider()
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
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
                        Column(
                            modifier = Modifier
                        ) {
                            BookCoverImage(
                                coverImage = it.coverUrl,
                                onClick = { goToDetail(it.id, it.name) })
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