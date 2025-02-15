package com.nabssam.bestbook.presentation.ui.book.ebook.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.nabssam.bestbook.R
import com.nabssam.bestbook.presentation.ui.book.ebook.PDFItem

@Composable
fun PDFListItem(
    modifier: Modifier = Modifier,
    pdf: PDFItem,
    onItemClick: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(dimensionResource(R.dimen.book_height_home))
            .clickable { onItemClick() },
        elevation = CardDefaults.elevatedCardElevation(4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data("http://res.cloudinary.com/dniu1zxdq/image/upload/v1737962343/tdiudk2hhebt4faznxhq.jpg")
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .width(dimensionResource(R.dimen.book_width_home))
                        .height(dimensionResource(R.dimen.book_height_home))
                        .clip(shape = RoundedCornerShape(4.dp)),
                )
                Column(
                    modifier = Modifier
                        .padding(8.dp)
//                        .align(Alignment.CenterStart)
                ) {
                    Text(
                        text = pdf.name,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    if (pdf.isDownloaded) {
                        Text(
                            text = "Downloaded - Click to Open",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Green
                        )
                    } else {
                        Text(
                            text = "Click to Download",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Blue
                        )
                    }
                }
            }

            IconButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd),
                onClick = onItemClick
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(
                        if (pdf.isDownloaded)
                            R.drawable.baseline_download_done_24
                        else R.drawable.download
                    ),
                    contentDescription = null
                )
            }
        }

    }
}