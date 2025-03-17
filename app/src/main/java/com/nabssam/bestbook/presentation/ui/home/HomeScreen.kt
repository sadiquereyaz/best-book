package com.nabssam.bestbook.presentation.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nabssam.bestbook.presentation.ui.components.ErrorScreen
import com.nabssam.bestbook.presentation.ui.components.VerticalSpacer
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

            item {
                VerticalSpacer(20)
                /*Banner(
                    fetchingBanners = state.fetchingBanners,
                    fetchedBanners = state.fetchedBanners
                )*/
            }

            // Recommended Books Section
            item {
                HomeScreenRowItem(
                    modifier = Modifier,
                    title = "Recommended for you",
                    onClick = { onAllBookSelect(state.randomTarget ?: "all") }) {
                    HorizontalBookList(
                        modifier = Modifier,
                        state.fetchingBooks,
                        state.fetchedBooks,
                        state.fullScreenError,
                        onNavigateToBook,
                        { event(EventHomeScreen.Initialize) }
                    )
                }
            }

            // Mock Tests Section
            item {
                    MockTests(navigateToMock = onContestSelect)
            }

            // PYQs Row Section
            item {
                HomeScreenRowItem(
                    modifier = Modifier,
                    title = "Free PYQs",
                    onClick = { onAllBookSelect("Free PYQs") }
                ){
                    PyqRow(
                        pyqList = state.fetchedPyq,
                        navigateToPyq = onNavigateToBook
                    )
                }
            }
        }
    }
}

