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
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.nabssam.bestbook.R
import com.nabssam.bestbook.presentation.ui.book.bookList.composable.CategoryChipList
import com.nabssam.bestbook.presentation.ui.book.bookList.composable.SearchBarComposable
import com.nabssam.bestbook.presentation.ui.components.BookCoverImage
import com.nabssam.bestbook.presentation.ui.components.BookTitlePrice
import com.nabssam.bestbook.presentation.ui.components.ErrorScreen
import com.nabssam.bestbook.presentation.ui.components.FullScreenProgressIndicator
import com.nabssam.bestbook.presentation.ui.components.VerticalSpacer

@Composable
fun BookListScreen(
    modifier: Modifier = Modifier,
    state: StateBookList,
    goToDetail: (String, String) -> Unit,
    onEvent: (EventBookList) -> Unit,
) {
    var showDropDown by remember { mutableStateOf(false) }

    var checkedState by remember {
        mutableStateOf(mapOf<String, Boolean>().apply {
            //state.categories.forEach { this[it] = false }
        })
    }

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
                    .padding(12.dp, 0.dp, 12.dp, 0.dp)
                    .wrapContentWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SearchBarComposable(
                    modifier = Modifier
                        .weight(1f),
                    books = books,
                    onBookSelected = { selectedBook = it }
                )
                //Filter
                IconButton(
                    modifier = Modifier
                        .offset(y = 4.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surface)/*.align(Alignment.CenterVertically)*/,
                    onClick = {
                        showDropDown = !showDropDown
                    }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.sort_icon),
                        contentDescription = "sort btn"
                    )
                    DropdownMenu(
                        modifier = Modifier.height(256.dp),
                        expanded = showDropDown,
                        onDismissRequest = {
                            showDropDown = false
                            onEvent(EventBookList.SortBy(/*state.sortCategoiries.forEach*/"JEE"))
                        },
                        offset = DpOffset(0.dp, 12.dp)
                    ) {
                        state.categories.forEach {
                            DropdownMenuItem(
                                text = { Text(it) },
                                onClick = {},
                                trailingIcon = {
                                    Checkbox(
                                        checked = checkedState[it] ?: false,
                                        onCheckedChange = { isChecked ->
                                            checkedState = checkedState.toMutableMap().apply {
                                                this[it] = isChecked
                                            }
                                        }
                                    )
                                }
                            )
                        }
                    }
                }
            }
            AnimatedVisibility(
                visible = checkedState.any { it.value },
                enter = fadeIn(animationSpec = tween(500)) + slideInVertically(),
                exit = fadeOut(animationSpec = tween(500)) + slideOutVertically()
            ) {
                CategoryChipList(
                    modifier = Modifier.padding(16.dp, 0.dp),
                    examList = /*state.categories*/checkedState.filter { it.value }.map { it.key },
                    onClick = { /*onEvent(EventBookList.SortBy(it))*/
                        checkedState = checkedState.toMutableMap().apply {
                            this[it] = false
                        }
                    }
                )
            }
            HorizontalDivider(modifier = if (!checkedState.any { it.value }) Modifier.padding(top = 12.dp) else Modifier)
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp, 12.dp, 12.dp, 0.dp),
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