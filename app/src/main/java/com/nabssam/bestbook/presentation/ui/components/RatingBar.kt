package com.nabssam.bestbook.presentation.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.nabssam.bestbook.R
import kotlin.math.ceil
import kotlin.math.floor

@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    rating: Double = 0.0,
    stars: Int = 5,
    starsColor: Color = Color(0.0f, 0.808f, 0.0f, 1.0f),
    showRate: Boolean = true
) {
    val filledStars = floor(rating).toInt()
    val unfilledStars = (stars - ceil(rating)).toInt()
    val halfStar = !(rating.rem(1).equals(0.0))
    Row(modifier = modifier) {
        repeat(filledStars) { Icon(imageVector = Icons.Outlined.Star, contentDescription = null, tint = starsColor) }
        if (halfStar) Icon(imageVector = ImageVector.vectorResource(id = R.drawable.star_halfs), contentDescription = null, tint = starsColor)

        repeat(unfilledStars) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.star),
                contentDescription = null,
                tint = starsColor
            )
        }
        if (showRate)
        Text(text = "($rating)", modifier = Modifier.padding(horizontal = 4.dp))
    }
}