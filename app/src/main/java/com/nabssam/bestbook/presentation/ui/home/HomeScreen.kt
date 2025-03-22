package com.nabssam.bestbook.presentation.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.nabssam.bestbook.R
import com.nabssam.bestbook.presentation.ui.components.ErrorScreen
import com.nabssam.bestbook.presentation.ui.home.components.Banner
import com.nabssam.bestbook.presentation.ui.home.components.HomeScreenRowItem
import com.nabssam.bestbook.presentation.ui.home.components.HorizontalBookList
import com.nabssam.bestbook.presentation.ui.home.components.MockTests
import com.nabssam.bestbook.presentation.ui.home.components.PyqRow

@Composable
fun HomeScreen(
    state: StateHomeScreen,
    modifier: Modifier = Modifier,
    onAllBookSelect: (String) -> Unit,
    onNavigateToBook: (String) -> Unit,
    event: (EventHomeScreen) -> Unit,
    onContestSelect: () -> Unit,
) {
    val listState = rememberLazyListState()
    if (state.fullScreenError != null) {
        ErrorScreen(
            modifier = modifier,
            message = state.fullScreenError,
            onRetry = { event(EventHomeScreen.Initialize) }
        )
    } else {

        LazyColumn(
            state = listState,
            modifier = modifier
//                .background(MaterialTheme.colorScheme.surface)
                .padding(8.dp, 0.dp, 8.dp, 0.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            // banner
            item {
//                VerticalSpacer(20)
                    Banner(
                        modifier = Modifier
                            .height(dimensionResource(R.dimen.book_height_home))
                            .clip(RoundedCornerShape(8.dp)),
                        fetchingBanners = state.fetchingBanners,
                        fetchedBanners = state.fetchedBanners,
                        error = state.errorBanners
                    )
            }

            // Recommended Books Section
            if (state.fetchedBooks.isNotEmpty() || state.errorBooks != null)
            item {
                HomeScreenRowItem(
                    modifier = Modifier,
                    title = "Recommended for you",
                    icon = R.drawable.personalised,
                    onBookClick = { onAllBookSelect(state.randomTarget ?: "all") },
                    isLoading = state.fetchingBooks,
                    error = state.errorBooks,
                    content = {
                        HorizontalBookList(
                            modifier = Modifier,
                            state.fetchingBooks,
                            state.fetchedBooks,
                            state.fullScreenError,
                            onNavigateToBook,
                            { event(EventHomeScreen.Initialize) }
                        )
                    },
                )
            }

            // Mock Tests Section
            item {
                MockTests(navigateToMock = onContestSelect)
            }

            // PYQs Row Section
            if (state.fetchedPyq.isNotEmpty() || state.errorPyq != null)
            item {
                HomeScreenRowItem(
                    modifier = Modifier,
                    title = "Free PYQs",
                    onBookClick = { onAllBookSelect("Free PYQs") },
                    content = {
                        PyqRow(
                            pyqList = state.fetchedPyq,
                            navigateToPyq = onNavigateToBook
                        )
                    },
                    icon = R.drawable.pyq,
                    isLoading = state.fetchingBooks,
                    error = state.errorBooks,
                )
            }
        }
    }
}

