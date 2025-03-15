package com.nabssam.bestbook.presentation.ui.home.components

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.res.dimensionResource
import androidx.core.net.toUri
import com.nabssam.bestbook.R
import com.nabssam.bestbook.presentation.ui.components.AutoScrollingImagePager
import com.nabssam.bestbook.presentation.ui.components.FullScreenProgressIndicator

private const val TAG = "BANNER"

@Composable
fun Banner(
    fetchingBanners: Boolean,
    fetchedBanners: List<BannerItem>
) {

    if (fetchingBanners) {
        FullScreenProgressIndicator(modifier = Modifier.height(dimensionResource(R.dimen.banner_height)))
    } else {
        AutoScrollingImagePager(
            autoscroll = false,
            modifier = Modifier,
            imageList = fetchedBanners.map { it.imageUrl },
            height = dimensionResource(R.dimen.banner_height)
        )
    }
}

data class BannerItem(
    val imageUrl: String,
    val deepLink: String? = null
)
