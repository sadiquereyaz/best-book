package com.nabssam.bestbook.presentation.ui.home

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.nabssam.bestbook.R
import com.nabssam.bestbook.presentation.ui.components.AutoScrollingImagePager
import com.nabssam.bestbook.presentation.ui.components.ErrorScreen
import com.nabssam.bestbook.presentation.ui.components.FullScreenProgressIndicator
import com.nabssam.bestbook.presentation.ui.home.components.HomeBookList
import com.nabssam.bestbook.presentation.ui.home.components.QuizCard
import com.nabssam.bestbook.presentation.ui.home.components.colorList

@Composable
fun HomeScreen(
    state: StateHomeScreen,
    modifier: Modifier = Modifier.fillMaxSize(),
    onAllBookSelect: (String) -> Unit,
    onNavigateToBook: (String) -> Unit,
    onBannerClick: () -> Unit,
    navigateToQuiz: (Int) -> Unit,
    onAllQuizSelect: (String) -> Unit,
    event: (EventHomeScreen) -> Unit,
) {
//    val examViewModel = hiltViewModel<ExamViewModel>()
//    val examState = examViewModel.uiState.collectAsState()
    Column(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
    ) {
        if (state.fullScreenError != null) {
            ErrorScreen(
                modifier = modifier,
                message = state.fullScreenError,
                onRetry = { event(EventHomeScreen.Initialize) }
            )
        }
        else {
            if (state.fetchingBanners) {
                FullScreenProgressIndicator(modifier = Modifier.height(dimensionResource(R.dimen.banner_height)))
            } else {
                AutoScrollingImagePager(
                    modifier = Modifier,
                    imageList = state.fetchedBanners,
                    height = dimensionResource(R.dimen.banner_height)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Recommended for you", style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.weight(1f))
                TextButton(
                    onClick = { onAllBookSelect("DUMMY_EXAM_ID") }
                ) {
                    Text(text = "View all", style = MaterialTheme.typography.labelLarge)
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            if (state.fetchingBooks) {
                FullScreenProgressIndicator(modifier = Modifier.height(dimensionResource(R.dimen.book_height_home)))
            } else if (state.errorBooks != null) {
                ErrorScreen(
                    message = state.errorBooks ?: "Error occur while fetching books",
                    modifier = Modifier.height(dimensionResource(R.dimen.book_height_home)),
                    onRetry = { event(EventHomeScreen.FetchBook) }
                )
            } else {
                Log.d("BOOK Size", state.fetchedBooks.size.toString())
                HomeBookList(state, onNavigateToBook)
            }

            // quiz
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Question Bank", style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.weight(1f))
                TextButton(
                    onClick = {}
                ) {
                    Text(text = "View all quiz", style = MaterialTheme.typography.labelLarge)
                }
            }
            Spacer(modifier = Modifier.height(4.dp))

            //  quiz tile
            LazyHorizontalGrid(
                modifier = Modifier.height(100.dp),
                rows = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                itemsIndexed(state.fetchedExams) { index, exam -> // Use itemsIndexed
                    val color = colorList[index % colorList.size] // Get color from list
                    QuizCard(exam, color = color, onQuizSelect = { navigateToQuiz(0) }) // Pass color to QuizCard
                }

            }
        }
    }

}




