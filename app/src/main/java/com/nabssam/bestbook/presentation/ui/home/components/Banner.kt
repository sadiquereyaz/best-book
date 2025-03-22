package com.nabssam.bestbook.presentation.ui.home.components

import androidx.annotation.DimenRes
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.nabssam.bestbook.R
import com.nabssam.bestbook.domain.model.Banner
import com.nabssam.bestbook.presentation.ui.components.AutoScrollingImagePager
import com.nabssam.bestbook.presentation.ui.components.ErrorScreen
import com.nabssam.bestbook.presentation.ui.components.TranslucentLoader

private const val TAG = "BANNER"

@Composable
fun Banner(
    modifier: Modifier = Modifier,
    fetchingBanners: Boolean,
    fetchedBanners: List<Banner>,
    error: String? = null,
    height: Dp = dimensionResource(R.dimen.banner_height)
) {
    error?.let {
        ErrorScreen(
            modifier = modifier.height(height), message = it
        )
    } ?: if (fetchingBanners) {
        TranslucentLoader(
            modifier = modifier
                .height(height)
                .clip(shape = RoundedCornerShape(8.dp))
        )
    } else if (fetchedBanners.isNotEmpty()){
        AutoScrollingImagePager(
            autoscroll = false,
            modifier = modifier,
            imageList = fetchedBanners.map { it.imageLink },
            redirectLinkList = fetchedBanners.map { it.redirectLink },
            height = height
        )
    } else {}
}
