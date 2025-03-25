package com.nabssam.bestbook.presentation.ui.book.ebookList.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.clip
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
import com.nabssam.bestbook.presentation.ui.book.ebookList.Ebook
import com.nabssam.bestbook.presentation.ui.components.Loading
import com.nabssam.bestbook.utils.getRandomColor

@Composable
fun PDFListItem(
    modifier: Modifier = Modifier,
    pdf: Ebook,
    onItemClick: () -> Unit
) {
    val random = remember { getRandomColor() }
    var isDownloading by remember { mutableStateOf(false) }
    Card(
        modifier = modifier
            .fillMaxWidth()
            //.padding(vertical = 8.dp, horizontal = 16.dp)
            .border(width = 1.5.dp, color = random, shape = RoundedCornerShape(12.dp))
            .height(dimensionResource(R.dimen.book_height_home))
            .clickable {
                onItemClick()
                isDownloading = true
            },
        //elevation = CardDefaults.elevatedCardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = random.copy(alpha = 0.1f),
        )
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
                        .data(pdf.coverUrl)
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
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    pdf.author?.let {
                        Text(
                            text = it,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
            pdf.exam?.let {
                Text(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .offset(x = dimensionResource(R.dimen.book_width_home) + 8.dp, y = (-8).dp)
                        .clip(RoundedCornerShape(50))
                        .background(MaterialTheme.colorScheme.background)
//                        .border(1.dp, random, RoundedCornerShape(50))
                        .padding(horizontal = 8.dp),
                    text = it,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
//                    color = MaterialTheme.colorScheme.primary
                )
            }
            IconButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd),
                onClick = {
                    onItemClick()
                    isDownloading = true
                }
            ) {
                if (isDownloading && !pdf.isDownloaded)
                    Loading(modifier = Modifier.size(24.dp))
                else
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