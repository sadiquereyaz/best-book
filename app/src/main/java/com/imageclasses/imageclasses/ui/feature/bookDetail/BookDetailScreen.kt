package com.imageclasses.imageclasses.ui.feature.bookDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScopeInstance.align
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRowScopeInstance.align
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.imageclasses.imageclasses.R
import com.imageclasses.imageclasses.ui.components.AutoScrollingImagePager
import com.imageclasses.imageclasses.data.model.BookModel
import com.imageclasses.imageclasses.ui.components.BookTitlePrice
import com.imageclasses.imageclasses.ui.components.RatingBar
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNull.content

@Serializable
data class BookDetailRoute(val bookId: String)


@Composable
fun BookDetailScreen(
    modifier: Modifier = Modifier,
    bookId: String,
    purchaseHardCopy: (String) -> Unit,
    purchaseEbook: (String) -> Unit
) {
    val book = BookModel()
    //val book by viewModel.bookFlow.collectAsState()
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            //.padding(16.dp)
            .fillMaxWidth()
            .verticalScroll(scrollState),
    ) {

        AutoScrollingImagePager(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            autoscroll = false,
            imageList = listOf(
                R.drawable.book1,
                R.drawable.book1,
                R.drawable.book1,
                R.drawable.book1
            ),
            height = /*Dp.Unspecified*/ 460.dp
        )
        BookTitlePrice(
            modifier = Modifier.padding(horizontal = 8.dp),
            book = book, addToFontSize = 4
        )
        RatingBar(rating = 3.6, modifier = Modifier.padding(  8.dp))
        HorizontalDivider(thickness = 2.dp)
        ProductDetailList(book)
        HorizontalDivider(thickness = 2.dp, modifier = Modifier.padding(vertical = 8.dp))
        Text(
            text = "Product Details",
            modifier = Modifier
                .padding(start = 8.dp, top = 4.dp, bottom = 8.dp),
            fontWeight = FontWeight.Bold
        )
        Text(text = book.description, modifier = Modifier.padding(horizontal = 8.dp))
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        Button(
            // ...
        ) {
            // ...
        }
    }
}

@Composable
 fun ProductDetailList(book: BookModel) {
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
    Row(modifier = modifier
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
    var isPurchased by remember { mutableStateOf(false) }

    BookDetailScreen(
        bookId = "sample_book_id",
        purchaseHardCopy = { isPurchased = true },
        purchaseEbook = { isPurchased = true }
    )

    if (isPurchased) {
        Text("Purchased!") // Display a message after purchase
    }
}

