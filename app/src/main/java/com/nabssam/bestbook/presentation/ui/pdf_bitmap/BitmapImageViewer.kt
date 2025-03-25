package com.nabssam.bestbook.presentation.ui.pdf_bitmap

import android.graphics.Bitmap
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import coil.compose.AsyncImage

@Composable
fun BitmapImageViewer(
    page: Bitmap,
    modifier: Modifier = Modifier,

    ) {
    AsyncImage(
        model = page,
        contentDescription = null,
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(page.width.toFloat() / page.height.toFloat())
            .drawWithContent {
                drawContent()
            }
    )
}