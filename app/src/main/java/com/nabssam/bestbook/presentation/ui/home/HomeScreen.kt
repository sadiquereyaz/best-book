package com.nabssam.bestbook.presentation.ui.home

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.nabssam.bestbook.R
import com.nabssam.bestbook.presentation.ui.components.AutoScrollingImagePager
import com.nabssam.bestbook.presentation.ui.components.ErrorScreen
import com.nabssam.bestbook.presentation.ui.components.FullScreenProgressIndicator
import com.nabssam.bestbook.presentation.ui.home.components.QuizCard
import com.nabssam.bestbook.presentation.ui.home.components.customCardList

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
    // Display 4 items
    val pagerState = rememberPagerState(pageCount = { customCardList.size })

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
                onRetry = { event(EventHomeScreen.Retry) }
            )
        } else {

            if (state.fetchingBanners) {
//                    CircularProgressIndicator()
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
                    message = state.errorBooks!!,
                    modifier = Modifier.height(dimensionResource(R.dimen.book_height_home)),
                    onRetry = { event(EventHomeScreen.FetchBook) }
                )
            }else {
                LazyRow(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(state.fetchedBooks) {
                        Box(
                            modifier = Modifier
                                .clickable { onNavigateToBook(it.id) }
                                .border(
                                    width = 0.5.dp,
                                    shape = RoundedCornerShape(6.dp),
                                    color = Color.Black
                                )
                            //  .clip(shape = RoundedCornerShape(6.dp))
                            //.padding(6.dp)
                        ) {
                            AsyncImage(
                                //painter = painterResource(id = R.drawable.book1),
                                model = it.imageUrls[0],
                                contentDescription = null,
                                contentScale = ContentScale.FillBounds,
                                modifier = Modifier
                                    .width(dimensionResource(R.dimen.book_width_home))
                                    .height(dimensionResource(R.dimen.book_height_home))
                                    .clip(shape = RoundedCornerShape(6.dp)),
                            )
                        }
                    }
                }
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
                items(customCardList) { card ->
                    QuizCard(card, onQuizSelect = { navigateToQuiz(0) })
                }

            }
        }
    }

}




