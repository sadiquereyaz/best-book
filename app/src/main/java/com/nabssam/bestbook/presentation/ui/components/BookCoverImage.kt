package com.nabssam.bestbook.presentation.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter

@Composable
fun BookCoverImage(
    modifier: Modifier = Modifier,
    coverImage: String,
) {
    Card(
        modifier = modifier
            .aspectRatio(9 / 12f),
        border = BorderStroke(
            width = 0.1.dp,
            color = Color.Black
        ),
        shape = RoundedCornerShape(8.dp),
        onClick = {}, enabled = false
    ) {
        // State to track loading progress
        val painter = rememberAsyncImagePainter(model = coverImage)
        val painterState = painter.state

        // Image
        Image(
            painter = painter,
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxSize()
                .clip(shape = RoundedCornerShape(8.dp))
        )

        // Loader when image is loading
        if (/*painterState is AsyncImagePainter.State.Loading*/true) {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

