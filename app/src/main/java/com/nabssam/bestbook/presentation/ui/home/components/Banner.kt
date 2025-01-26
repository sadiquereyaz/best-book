package com.nabssam.bestbook.presentation.ui.home.components

import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.nabssam.bestbook.R
import com.nabssam.bestbook.presentation.ui.components.AutoScrollingImagePager
import com.nabssam.bestbook.presentation.ui.components.FullScreenProgressIndicator
import com.nabssam.bestbook.presentation.ui.home.StateHomeScreen

@Composable
fun Banner(state: StateHomeScreen) {
    if (state.fetchingBanners) {
        FullScreenProgressIndicator(modifier = Modifier.height(dimensionResource(R.dimen.banner_height)))
    } else {
        AutoScrollingImagePager(
            modifier = Modifier,
            imageList = state.fetchedBanners,
            height = dimensionResource(R.dimen.banner_height),
            autoscroll = false
        )
    }
}

