package com.nabssam.bestbook.presentation.ui.components

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield

private const val TAG = "AUTO_SCROLLING_IMAGE_PAGER"
@Composable
fun AutoScrollingImagePager(
    modifier: Modifier = Modifier,
    autoscroll: Boolean = true,
    imageList: List<String?> = emptyList(),
    redirectLinkList: List<String?> = emptyList(),
    height: Dp = 160.dp
) {
    val pageCount = imageList.size
    val pagerState = rememberPagerState(pageCount = { pageCount })

    val uriHandler = LocalUriHandler.current
    val context = LocalContext.current

    // Auto-scroll logic
    LaunchedEffect(pagerState) {
        while (autoscroll && pageCount > 1) {
            yield()
            delay(3000) // Change page every 3 seconds
            val nextPage = (pagerState.currentPage + 1) % pageCount
            pagerState.animateScrollToPage(nextPage)
        }
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                //.fillMaxWidth()
                .height(height)
        ) { page ->
            AsyncImage(
                model = imageList[page],
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxSize()
                    .clickable(
                        enabled = redirectLinkList[page] != null,
                        onClick = {
                            try {
                                // Used only for opening URIs (like web links or deep links) from Jetpack Compose
                                redirectLinkList[page]?.let {
                                    if (it.startsWith("http"))      // uri must include protocol like 'https'
                                        uriHandler.openUri(it)
                                } ?: throw Exception("Invalid redirect link")
                            } catch (e: Exception) {
                                // No app can handle this URI
                                Log.e(TAG, "error", e)
                                Toast.makeText(context, "Unable to open the link", Toast.LENGTH_SHORT).show()
                            }
                        }
                    )
                    .clip(shape = RoundedCornerShape(8.dp))
                    .height(height),
            )
        }

        // Pager Indicator
        Row(
            Modifier
                .wrapContentHeight()
                //.fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pagerState.pageCount) { iteration ->
                val color =
                    if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(10.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewImagePager(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
    ) {
        AutoScrollingImagePager(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .background(color = Color.Red),
            autoscroll = false,
            height = /*Dp.Unspecified*/ 460.dp
        )

    }
}