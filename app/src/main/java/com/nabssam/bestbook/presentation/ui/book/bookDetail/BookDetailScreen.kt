package com.nabssam.bestbook.presentation.ui.book.bookDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nabssam.bestbook.R
import com.nabssam.bestbook.domain.model.Book
import com.nabssam.bestbook.presentation.theme.BestBookTheme
import com.nabssam.bestbook.presentation.ui.components.AutoScrollingImagePager
import com.nabssam.bestbook.presentation.ui.components.BookTitlePrice
import com.nabssam.bestbook.presentation.ui.components.ErrorScreen
import com.nabssam.bestbook.presentation.ui.components.FullScreenProgressIndicator
import com.nabssam.bestbook.presentation.ui.components.RatingBar

enum class ButtonType(val btnText: String, val iconId: Int) {
    EBOOK("Buy Ebook", R.drawable.ebook),
    GO_TO_CART("Go to cart", R.drawable.go_to_cart),
    ADD_TO_CART(btnText = "Add to cart", R.drawable.add_cart)
}

@Composable
fun BookDetailScreen(
    modifier: Modifier = Modifier,
    purchaseEbook: (String) -> Unit,
    isEbook: Boolean = true,
    btnType: ButtonType = ButtonType.ADD_TO_CART,
    goToCart: () -> Unit = {},
    state: StateBookDetail,
    onEvent: (EventBookDetail) -> Unit
) {
    val scrollState = rememberScrollState()
    val bookObj = state.fetchedBook

    var btnState by remember { mutableStateOf(btnType) }

    if (state.loading)
        FullScreenProgressIndicator(modifier = modifier, message = "Loading...")
    else if (state.errorMessage != null)
        ErrorScreen(
            message = state.errorMessage ?: "Error occur while fetching book detail",
            modifier = modifier,
            onRetry = { onEvent(EventBookDetail.Retry) }
        )
    else {
        Column(
            modifier = modifier
                .padding(bottom = ButtonDefaults.MinHeight + 8.dp)
                .fillMaxWidth()
                .verticalScroll(scrollState),
        ) {

            AutoScrollingImagePager(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                autoscroll = false,
                imageList = (bookObj.imageUrls),
                height = /*Dp.Unspecified*/ 460.dp
            )
            BookTitlePrice(
                modifier = Modifier.padding(horizontal = 8.dp),
                addToFontSize = 4,
                discPer = bookObj.hardCopyDis,
                originalPrice = bookObj.price,
                title = bookObj.name,
                maxLine = 3
            )
            RatingBar(rating = 3.6, modifier = Modifier.padding(8.dp))
            HorizontalDivider(thickness = 2.dp)
            BookDetailList(bookObj)
            HorizontalDivider(
                thickness = 2.dp,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Text(
                text = "Description",
                modifier = Modifier
                    .padding(start = 8.dp, top = 4.dp, bottom = 8.dp),
                fontWeight = FontWeight.Bold
            )
            Text(
                text = bookObj.description,
                modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 24.dp)
            )
        }

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
                    when (btnState) {
                        ButtonType.EBOOK -> purchaseEbook(bookObj.id)

                        ButtonType.ADD_TO_CART -> {
                            EventBookDetail.AddToCart
                            btnState = ButtonType.GO_TO_CART
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
                    Icon(ImageVector.vectorResource(btnState.iconId), btnState.btnText)
                    Text(
                        text = btnState.btnText,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

        }

    }
}

@Composable
fun BookDetailList(book: Book) {
    Text(
        text = "Product Details",
        modifier = Modifier
            .padding(start = 8.dp, top = 4.dp, bottom = 8.dp),
        fontWeight = FontWeight.Bold
    )
    ProductDetail(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surfaceContainerLow),
        headText = "Author",
        tailText = book.author
    )
    ProductDetail(
        modifier = Modifier,
        headText = "Publishing date",
        tailText = book.publishDate
    )
    ProductDetail(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surfaceContainerLow),
        headText = "Publisher",
        tailText = book.publisher
    )
    ProductDetail(
        modifier = Modifier,
        headText = "No. of pages",
        tailText = "${book.noOfPages}"
    )
    ProductDetail(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surfaceContainerLow),
        headText = "Language",
        tailText = book.language
    )
}

@Composable
fun ProductDetail(modifier: Modifier, headText: String = "head", tailText: String = "tail") {
    Row(
        modifier = modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = headText,
            color = MaterialTheme.colorScheme.secondary
        )
        Text(
            modifier = Modifier.weight(1f),
            text = tailText
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BookDetailScreenPreview() {
    BestBookTheme {
        BookDetailScreen(
            state = StateBookDetail(
                fetchedBook = Book(
                    id = "1",
                    name = "Sample Book",
                    author = "Sample Author",
                    description = "This is a sample book description.",
                    price = 10,
                    imageUrls = listOf(
                        "https://example.com/image1.jpg",
                        "https://example.com/image2.jpg"
                    )
                )
            ),
            onEvent = {},
            purchaseEbook ={},
            isEbook = true,
            btnType = ButtonType.ADD_TO_CART,
            goToCart = {}
        )
    }
}
