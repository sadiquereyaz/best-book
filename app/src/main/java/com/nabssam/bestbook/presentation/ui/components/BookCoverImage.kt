package com.nabssam.bestbook.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.nabssam.bestbook.R
import com.nabssam.bestbook.presentation.theme.Star

@Composable
fun BookCoverImage(
    modifier: Modifier = Modifier,
    coverImageUrl: String,
    onClick: () -> Unit,
    rate: Double =  0.0,
) {
    ElevatedCard(
        modifier = modifier
            .aspectRatio(9 / 12f),
        /*border = BorderStroke(
            width = 0.1.dp,
            color = Color.Black
        ),*/
        shape = RoundedCornerShape(8.dp),
        onClick = { onClick() }
    ) {
        // State to track loading progress
        val painter = rememberAsyncImagePainter(model = coverImageUrl)
        val painterState = painter.state

        Box() {

            // Loader when image is loading
            if (painterState is AsyncImagePainter.State.Loading) {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
            Image(
                painter = painter,
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(shape = RoundedCornerShape(8.dp))
            )
            if (rate > 0.0)
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(4.dp)
                        .background(
                            color = MaterialTheme.colorScheme.surface,
                            shape = RoundedCornerShape(100)
                        )
                        .padding(horizontal = 4.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier.size(18.dp),
                            imageVector = Icons.Filled.Star,
                            contentDescription = "star",
                            tint = colorResource(R.color.star_yellow),
                        )
                        Text(
                            modifier = Modifier.padding(horizontal = 1.dp),
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
                            text = "|"
                        )
                        Text(
                            text = rate.toString(),
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }

                }
        }
    }
}

