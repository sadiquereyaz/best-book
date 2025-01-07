package com.nabssam.bestbook.presentation.ui.book.bookList

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
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.nabssam.bestbook.presentation.ui.components.BookTitlePrice

@Composable
fun BookListScreen(
    state: StateBookList,
    modifier: Modifier = Modifier,
    onNavigateToBook: (String) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier
            .fillMaxSize()
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(state.fetchedBooks!!) {
            Box(
                modifier = Modifier
                    .clickable { onNavigateToBook(it.id.toString()) },
                contentAlignment = Alignment.TopCenter
            ) {
                Column {
                    BookCoverImage(coverImage = it.imageUrls[0])
                    //book title and price
                    BookTitlePrice()
                }
            }
        }

    }
}


@Composable
fun BookCoverImage(
    modifier: Modifier = Modifier,
    coverImage: String,
) {
    Box(modifier = modifier) {
        // State to track loading progress
        val painter = rememberAsyncImagePainter(model = coverImage)
        val painterState = painter.state

        // Image
        Image(
            painter = painter,
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
        )

        // Loader when image is loading
        if (painterState is AsyncImagePainter.State.Loading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = MaterialTheme.colorScheme.primary
            )
        }
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
            state = StateBookList(),
            modifier = Modifier.padding(it)
        ) { }
    }
}
