package com.nabssam.bestbook.ui.feature.bookList

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nabssam.bestbook.R
import com.nabssam.bestbook.data.model.BookModel
import com.nabssam.bestbook.ui.components.BookTitlePrice

@Composable
fun BookListScreen(
    modifier: Modifier = Modifier,
    onNavigateToBook: (Int) -> Unit
) {
    val listOfBooks = listOf(
        "Book1",
        "Book2",
        "Book3",
        "Book4",
        "Book5",
        "Book6",
        "Book7",
        "Book8",
        "Book9",
    );
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier
            .fillMaxSize()
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        val book: BookModel = BookModel()
        repeat(10) {
            item {
                Box(
                    modifier = Modifier
                        .clickable { onNavigateToBook(book.bookId) },
                    contentAlignment = Alignment.TopCenter
                ) {
                    Column {
                        BookCoverImage(/*modifier*/)
                        //book title and price
                        BookTitlePrice(book=book)
                    }
                }
            }

        }
    }
}

@Composable
 fun BookCoverImage(
     modifier: Modifier = Modifier
 ) {
   Box(modifier = modifier) {
        Image(
            painter = painterResource(id = R.drawable.book1),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = modifier
                .aspectRatio(9 / 12f)
                .border(
                    width = 0.1.dp,
                    shape = RoundedCornerShape(8.dp),
                    color = Color.Black
                )
                .clip(shape = RoundedCornerShape(8.dp))
            //.padding(start = 4.dp),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun BookListPreview() {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Books") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
            )
        },
    ) { it ->
        BookListScreen(
            modifier = Modifier.padding(it)
        ) { }
    }
}
