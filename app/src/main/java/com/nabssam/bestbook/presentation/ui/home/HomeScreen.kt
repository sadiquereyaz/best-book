package com.nabssam.bestbook.presentation.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nabssam.bestbook.presentation.ui.components.ErrorScreen
import com.nabssam.bestbook.presentation.ui.home.components.Banner
import com.nabssam.bestbook.presentation.ui.home.components.HorizontalBookList
import com.nabssam.bestbook.presentation.ui.home.components.MockTests
import com.nabssam.bestbook.presentation.ui.home.components.QuizRow

@Composable
fun HomeScreen(
    state: StateHomeScreen,
    modifier: Modifier = Modifier,
    onAllBookSelect: (String) -> Unit,
    onNavigateToBook: (String) -> Unit,
    navigateToQuiz: (String) -> Unit,
    onAllQuizSelect: (String) -> Unit = {},
    event: (EventHomeScreen) -> Unit,
    onContestSelect: () -> Unit,
) {
    if (state.fullScreenError != null) {
        ErrorScreen(
            modifier = modifier,
            message = state.fullScreenError,
            onRetry = { event(EventHomeScreen.Initialize) }
        )
    } else {

        LazyColumn(
            modifier = modifier
                .padding(8.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            item {
                Banner(
                    fetchingBanners = state.fetchingBanners,
                    fetchedBanners = state.fetchedBanners
                )
            }

            // Recommended Books Section
            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Recommended for you",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    TextButton(
                        onClick = { onAllBookSelect(state.randomTarget ?: "all") },
                        contentPadding = PaddingValues(0.dp),
                    ) {
                        Text(
                            text = "View all",
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                }
            }

            item {
                HorizontalBookList(
                    modifier = Modifier,
                    state.fetchingBooks,
                    state.fetchedBooks,
                    state.fullScreenError,
                    onNavigateToBook,
                    { event(EventHomeScreen.Initialize) }
                )
            }

            // Mock Tests Section
            item {
                MockTests(navigateToMock = { onContestSelect() })
            }

            // Quiz Row Section
            item {
                QuizRow(
                    navigateToQuiz = {},
                    navigateToAllQuiz = { onAllQuizSelect("all") }
                )
            }
        }
    }
}
