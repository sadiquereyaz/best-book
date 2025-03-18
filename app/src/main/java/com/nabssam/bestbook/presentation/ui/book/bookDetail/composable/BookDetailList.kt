package com.nabssam.bestbook.presentation.ui.book.bookDetail.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nabssam.bestbook.domain.model.Book

@Composable
fun BookDetailList(book: Book) {
    HorizontalDivider(
        thickness = 2.dp,
        modifier = Modifier.padding(bottom = 4.dp, top = 12.dp)
    )
    Text(
        text = "Book Details",
        modifier = Modifier
            .padding(start = 8.dp, top = 4.dp, bottom = 8.dp),
        fontWeight = FontWeight.Bold
    )
    book.author?.let {
        ProductDetail(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surfaceContainerLow),
        headText = "Author",
        tailText = it
    )
    }
    book.publishDate?.let {
        ProductDetail(
        modifier = Modifier,
        headText = "Publishing date",
        tailText = it
    )
    }
    book.publisher?.let {
        ProductDetail(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surfaceContainerLow),
        headText = "Publisher",
        tailText = it
    )
    }
    book.noOfPages?.let {
        ProductDetail(
            modifier = Modifier,
            headText = "No. of pages",
            tailText = "$it"
        )
    }
    book.language?.let {
        ProductDetail(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surfaceContainerLow),
        headText = "Language",
        tailText = it
    )
    }
}