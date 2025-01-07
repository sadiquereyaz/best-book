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
import androidx.compose.runtime.collectAsState
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
import com.nabssam.bestbook.presentation.ui.components.AutoScrollingImagePager
import com.nabssam.bestbook.presentation.ui.components.BookTitlePrice
import com.nabssam.bestbook.presentation.ui.components.ErrorScreen
import com.nabssam.bestbook.presentation.ui.components.FullScreenProgressIndicator
import com.nabssam.bestbook.presentation.ui.components.RatingBar
import com.nabssam.bestbook.utils.Resource

enum class ButtonType(val btnText: String, val iconId: Int) {
    EBOOK("Buy Ebook", R.drawable.ebook),
    GO_TO_CART("Go to cart", R.drawable.go_to_cart),
    ADD_TO_CART(btnText = "Add to cart", R.drawable.add_cart)
}

@Composable
fun ProductDetailScreen(
    modifier: Modifier = Modifier,
    //bookId: Int,
    purchaseEbook: (String) -> Unit,
    isEbook: Boolean = true,
    btnType: ButtonType = ButtonType.ADD_TO_CART,
    goToCart: () -> Unit = {},
    vm: ViewModelBookDetail
) {
    //val book = Book()
    val state = vm.state.collectAsState()
    val scrollState = rememberScrollState()

    var btnState by remember { mutableStateOf(btnType) }
    when (state.value) {
        is Resource.Loading -> {
            FullScreenProgressIndicator(modifier = modifier)
        }

        is Resource.Error -> {
            val error = (state.value as Resource.Error<Book>).message ?: "An Error Occurred"
            ErrorScreen(
                message = error,
                modifier = modifier,
                onRetry = { vm.fetchBookDetail() }
            )
        }

        is Resource.Success -> {
            (state.value as Resource.Success<Book>).data?.let {
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
                        imageList = (it.imageUrls),
                        height = /*Dp.Unspecified*/ 460.dp
                    )
                    BookTitlePrice(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        addToFontSize = 4
                    )
                    RatingBar(rating = 3.6, modifier = Modifier.padding(8.dp))
                    HorizontalDivider(thickness = 2.dp)
                    ProductDetailList(it)
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
                        text = it.description,
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
                                ButtonType.EBOOK -> purchaseEbook(it.id)

                                ButtonType.ADD_TO_CART -> {
                                    vm.addToCart()
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
    }
}

@Composable
fun ProductDetailList(book: Book) {
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BookDetailScreenPreview() {
    ProductDetailScreen(
        modifier = Modifier,
        //bookId = 123,
        purchaseEbook = { /* No-op for preview */ },
        isEbook = true,
        btnType = ButtonType.ADD_TO_CART, // Assuming you have a ButtonType enum
        goToCart = {},
        vm = TODO(),
    )
}