package com.nabssam.bestbook.presentation.ui.home.components

import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.nabssam.bestbook.R
import com.nabssam.bestbook.domain.model.Banner
import com.nabssam.bestbook.presentation.ui.components.AutoScrollingImagePager
import com.nabssam.bestbook.presentation.ui.components.FullScreenProgressIndicator

private const val TAG = "BANNER"

@Composable
fun Banner(
    fetchingBanners: Boolean,
    fetchedBanners: List<Banner>
) {
    if (fetchingBanners) {
        FullScreenProgressIndicator(modifier = Modifier.height(dimensionResource(R.dimen.banner_height)))
    } else {
        AutoScrollingImagePager(
            autoscroll = true,
            modifier = Modifier,
            imageList = fetchedBanners.map { it.imageLink },
            redirectLinkList = fetchedBanners.map { it.redirectLink },
            height = dimensionResource(R.dimen.banner_height)
        )
    }
}
