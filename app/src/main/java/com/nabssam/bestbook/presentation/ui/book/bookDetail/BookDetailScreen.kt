package com.nabssam.bestbook.presentation.ui.book.bookDetail

import PurchaseOptionBox
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nabssam.bestbook.presentation.ui.book.bookDetail.composable.BookDetailList
import com.nabssam.bestbook.presentation.ui.book.bookDetail.composable.RelatedBookList
import com.nabssam.bestbook.presentation.ui.book.bookDetail.composable.Review
import com.nabssam.bestbook.presentation.ui.components.AutoScrollingImagePager
import com.nabssam.bestbook.presentation.ui.components.ErrorScreen
import com.nabssam.bestbook.presentation.ui.components.FullScreenProgressIndicator
import com.nabssam.bestbook.presentation.ui.components.RatingBar
import kotlinx.coroutines.launch

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import com.nabssam.bestbook.presentation.ui.book.bookDetail.composable.BookDescription


@Composable
fun BookDetailScreen(
    modifier: Modifier = Modifier,
    goToCart: () -> Unit = {},
    state: StateBookDetail,
    onEvent: (EventBookDetail) -> Unit,
    onSeeAllReviewClick: () -> Unit,
    showBooksByExam: () -> Unit,
    navigateToBookDetail: (String) -> Unit,
    navigateToPayment: (String) -> Unit = {}
) {
    val lazyListState = rememberLazyListState()
    val bookObj = state.fetchedBook
    val coroutineScope = rememberCoroutineScope()

    if (state.loading) {
        FullScreenProgressIndicator(modifier = modifier, message = "Loading...")
    } else if (state.errorMessage != null) {
        ErrorScreen(
            message = state.errorMessage ?: "Error occurred while fetching book details",
            modifier = modifier,
            onRetry = { onEvent(EventBookDetail.Retry) }
        )
    } else {
        LazyColumn(
            state = lazyListState,
            modifier = modifier
                .padding(bottom = ButtonDefaults.MinHeight + 8.dp)
                .fillMaxWidth(),
        ) {
            // Exam , Rating and title
            item {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = bookObj.exam,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.clickable { showBooksByExam() }
                    )
                    bookObj.rate?.let {
                        RatingBar(modifier = Modifier.clickable {
                            coroutineScope.launch {
                                lazyListState.animateScrollToItem(5) // Scroll to Reviews section
                            }
                        }, rating = it.points, count = it.count)
                    }
                }
            // Title
                Text(
                    modifier = modifier
                        .padding(8.dp, 4.dp),
                    text = bookObj.name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 17.sp
                )
            }

            // Auto-Scrolling Image Pager
            item {
                AutoScrollingImagePager(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    autoscroll = false,
                    imageList = bookObj.imageUrls,
                    height = 460.dp
                )
            }

            // book purchase option tab
            item {
                PurchaseOptionBox(
                    paperbackPrice = bookObj.hardcopyPrice,
                    paperbackDiscount = bookObj.hardCopyDis,
                    ebookPrice = bookObj.ebookPrice,
                    ebookDiscount = bookObj.ebookDis,
                    onTabSelect = { btnState ->
                        onEvent(EventBookDetail.BookTypeSelect(btnState))
                    },
//                    onEvent = onEvent,
                )
            }

            //  Description
            item {
                BookDescription(bookObj.description, modifier = Modifier.padding(top = 12.dp))
            }

            // Book Details
            item {
                BookDetailList(bookObj)
            }

            // Related Books
            item {
                RelatedBookList(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    state = state,
                    onNavigateToBook = navigateToBookDetail
                )
            }

            // Reviews
            item {
                bookObj.rate?.reviews?.let {
                    Review(
                        onSeeAllReviewClick = onSeeAllReviewClick,
                        reviewList = it,
                        modifier = Modifier.padding(top = 12.dp)
                    )
                }
            }

            // Spacer to ensure scrolling above the button
            item {
                Spacer(Modifier.height(64.dp))
            }
        }
    }

    // Floating Button at the Bottom
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp), contentAlignment = Alignment.BottomCenter
    ) {
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(0.dp)),
            onClick = {
                when (state.buttonState) {
                    ButtonType.EBOOK -> navigateToPayment(bookObj.id)
                    ButtonType.ADD_TO_CART -> {
                        onEvent(EventBookDetail.AddToCart)
                    }
                    ButtonType.GO_TO_CART -> goToCart()
                }
            }
        ) {
            Row(
                modifier = Modifier.padding(vertical = 2.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(ImageVector.vectorResource(state.buttonState.iconId), state.buttonState.btnText)
                Text(
                    text = state.buttonState.btnText,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
