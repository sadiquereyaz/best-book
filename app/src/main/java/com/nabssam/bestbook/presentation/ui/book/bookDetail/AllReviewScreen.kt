package com.nabssam.bestbook.presentation.ui.book.bookDetail

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.nabssam.bestbook.presentation.ui.book.bookDetail.composable.ReviewItem

private const val TAG = "ALL_REVIEW_SCREEN"

@Composable
fun AllReviewScreen(
    modifier: Modifier = Modifier,
    uiState: StateBookDetail
) {
    val lazyListState = rememberLazyListState()
    LazyColumn(
        state = lazyListState,
        modifier = modifier
    ) {
        // Reviews
        items(uiState.reviewsList) {
            ReviewItem(
                review = it,
                modifier = Modifier,
            )
        }
    }
}
