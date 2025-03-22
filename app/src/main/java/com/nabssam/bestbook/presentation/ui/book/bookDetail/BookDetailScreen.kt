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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import com.nabssam.bestbook.presentation.ui.components.GradientButton
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nabssam.bestbook.R
import com.nabssam.bestbook.presentation.ui.book.bookDetail.composable.BookDescription
import com.nabssam.bestbook.presentation.ui.book.bookDetail.composable.BookDetailList
import com.nabssam.bestbook.presentation.ui.book.bookDetail.composable.RelatedBookList
import com.nabssam.bestbook.presentation.ui.book.bookDetail.composable.ReviewList
import com.nabssam.bestbook.presentation.ui.components.AutoScrollingImagePager
import com.nabssam.bestbook.presentation.ui.components.ErrorScreen
import com.nabssam.bestbook.presentation.ui.components.TranslucentLoader
import com.nabssam.bestbook.presentation.ui.components.RatingBar
import com.nabssam.bestbook.presentation.ui.components.WriteRateReview
import kotlinx.coroutines.launch

private const val TAG = "BOOK_DETAIL_SCREEN"

@Composable
fun BookDetailScreen(
    modifier: Modifier = Modifier,
    goToCart: () -> Unit = {},
    state: StateBookDetail,
    onEvent: (EventBookDetail) -> Unit,
    onSeeAllReviewClick: () -> Unit,
    showBooksByExam: () -> Unit,
    navigateToBookDetail: (String) -> Unit,
    submitReview: () -> Unit,
    deleteReview: (String) -> Unit,
    updateRateReview: (Int, String) -> Unit
) {
    val lazyListState = rememberLazyListState()
    val bookObj = state.fetchedBook
    val coroutineScope = rememberCoroutineScope()
    val reviewModifier = Modifier.height(dimensionResource(R.dimen.book_height_home))
    val isBtnEnable by remember { mutableStateOf(state.productType != null) }
    LaunchedEffect(state.productType) {

    }

    Box(
        modifier = modifier.fillMaxSize()
    ) {

        if (state.errorMessage != null) {
            ErrorScreen(
                message = state.errorMessage ?: "Error occurred while fetching book details",
                modifier = modifier,
                onRetry = { onEvent(EventBookDetail.Initialize) }
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
                        bookObj.exam?.let {
                            Text(
                                text = it,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.clickable { showBooksByExam() }
                            )
                        }
                        if (state.showRating) {
                            RatingBar(modifier = Modifier.clickable {
                                coroutineScope.launch {
                                    lazyListState.animateScrollToItem(5) // Scroll to Reviews section
                                }
                            }, rating = bookObj.averageRate ?: 0.0, count = bookObj.reviewCount)
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
                if (bookObj.imageUrls.isNotEmpty() || !bookObj.coverUrl.isNullOrBlank()) {
//                    Log.d(TAG, "image list: ${bookObj.imageUrls.size} ${bookObj.imageUrls}")
//                    Log.d(TAG, "cover: ${bookObj.coverUrl}")
                    item {
                        AutoScrollingImagePager(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            autoscroll = false,
                            imageList = if (bookObj.imageUrls.isNotEmpty()) bookObj.imageUrls else listOf(
                                bookObj.coverUrl
                            ),
                            height = 480.dp
                        )
                    }
                }

                // book purchase option tab
                item {
                    PurchaseOptionBox(
                        paperbackPrice = bookObj.hardcopyPrice,
                        paperbackDiscount = bookObj.hardCopyDis,
                        ebookPrice = bookObj.ebookPrice,
                        ebookDiscount = bookObj.ebookDis,
                        productType = state.productType,
                        originalPrice = state.fetchedBook.price,
                        onTypeSelect = { productType ->
                            onEvent(EventBookDetail.UpdateButtonState(ButtonType.ADD_TO_CART))
                            onEvent(EventBookDetail.ProductTypeSelect(productType))
                        },
                    )
                }

                //  Description
                item {
                    bookObj.description?.let {
                        BookDescription(
                            it,
                            modifier = Modifier.padding(top = 12.dp)
                        )
                    }
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
                    HorizontalDivider(
                        thickness = 2.dp,
                        modifier = Modifier.padding(/*bottom = 8.dp,*/ top = 16.dp)
                    )
                    if (state.reviewLoading)
                        TranslucentLoader(modifier = modifier, message = "Loading...")
                    else if (state.reviewError != null)
                        ErrorScreen(
                            modifier = reviewModifier,
                            message = state.errorMessage ?: "Error occurred while fetching reviews",
                        )
                    if (state.reviewsList.isNotEmpty())
                        ReviewList(
                            reviewList = state.reviewsList,
                            onSeeAllReviewClick = onSeeAllReviewClick,
                            modifier = Modifier,
                            onDeleteReview = deleteReview
                        )
                    else
                        Text("No review found", modifier = Modifier.padding(horizontal = 8.dp))
                }

                //write review
                item {
                    /*HorizontalDivider(
                        modifier = Modifier
//                        .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    )*/
                    WriteRateReview(
                        modifier = Modifier,
                        submitReview = submitReview,
                        updateRateReview = updateRateReview
                    )
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
            GradientButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(0.dp)),
                enabled = state.productType != null,
                onClick = {
                    when (state.buttonState) {
//                    ButtonType.EBOOK -> navigateToPayment(bookObj.id)
                        ButtonType.ADD_TO_CART -> {
                            onEvent(EventBookDetail.ButtonClick)
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
                    Icon(
                        ImageVector.vectorResource(state.buttonState.iconId),
                        state.buttonState.btnText
                    )
                    Text(
                        text = state.buttonState.btnText,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
        if (state.loading) {
            TranslucentLoader(
                message = "Loading..."
            )
        }
    }
}
