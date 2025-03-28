package com.nabssam.bestbook.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import com.nabssam.bestbook.R
import kotlin.math.ceil
import kotlin.math.floor

@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    rating: Double,
    totalStars: Int = 5,
    starsColor: Color = colorResource(R.color.star_yellow),
    count: Int? = null,
) {
    val filledStars = floor(rating).toInt()
    val unfilledStars = (totalStars - ceil(rating)).toInt()
    val halfStar = !(rating.rem(1).equals(0.0))
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(filledStars) { Icon(imageVector = Icons.Outlined.Star, contentDescription = null, tint = starsColor) }
        if (halfStar) Icon(imageVector = ImageVector.vectorResource(id = R.drawable.star_halfs), contentDescription = null, tint = starsColor)

        repeat(unfilledStars) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.star),
                contentDescription = null,
                tint = starsColor
            )
        }
        count?.let {Text(text = "($it)", /*modifier = Modifier.padding(horizontal = 4.dp)*/)}
    }
}