package com.nabssam.bestbook.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.nabssam.bestbook.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield

@Composable
fun AutoScrollingImagePager(
    autoscroll: Boolean = true,
    imageList: List<Int> = listOf( R.drawable.banner1, R.drawable.banner1, R.drawable.banner1, R.drawable.banner3 ), // List of image resource IDs
    modifier: Modifier = Modifier,
    height: Dp = 160.dp
) {
    val pagerState = rememberPagerState(pageCount = { imageList.size })

    // Auto-scroll logic
    LaunchedEffect(pagerState) {
        while (autoscroll) {
            yield()
            delay(2000) // Change page every 3 seconds
            val nextPage = (pagerState.currentPage + 1) % imageList.size
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
                Image(
                    painter = painterResource(id = imageList[page]),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                       .fillMaxSize()
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
fun PreviewImagePager(modifier:Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize()
            .fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
    ) {
        AutoScrollingImagePager(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .background(color = Color.Red),
            autoscroll = false,
            imageList = listOf(
                R.drawable.book1,
                R.drawable.book1,
                R.drawable.book1,
                R.drawable.book1
            ),
            height = /*Dp.Unspecified*/ 460.dp
        )

    }
}