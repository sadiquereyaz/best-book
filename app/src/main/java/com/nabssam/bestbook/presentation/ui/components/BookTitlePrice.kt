package com.nabssam.bestbook.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nabssam.bestbook.R

@Composable
fun BookTitlePrice(
    modifier: Modifier = Modifier,
    addToFontSize: Int = 0,
    padTop: Dp = 8.dp,
    maxLine: Int,
    discPer: Int,
    originalPrice: Int,
    title: String? = null,
    rating: Double? = null
) {
    Column(modifier = modifier) {
        //title
       title?.let {
           Text(
               modifier = modifier
                   .padding(top = padTop, bottom = 4.dp),
               text = title,
               fontSize = (14 + addToFontSize).sp,
               fontWeight = FontWeight.Bold,
               maxLines = maxLine,
               overflow = TextOverflow.Ellipsis,
//                            textAlign = TextAlign.Center,
               lineHeight = 17.sp
           )
       }
        //price row
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            //discount %
            Text(
                modifier = Modifier,
                text = "↓${discPer}%",
                fontSize = (16 + addToFontSize).sp,
                fontWeight = FontWeight.ExtraBold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
//                            textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary
            )
            // original price
            Text(
                modifier = Modifier,
                text = "₹${originalPrice}",
                fontSize = (14 + addToFontSize).sp,
                fontWeight = FontWeight.Normal,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textDecoration = TextDecoration.LineThrough,
//                            textAlign = TextAlign.Center
            )
            // final price
            Text(
                modifier = Modifier,
                text = "₹${(originalPrice - (discPer * originalPrice) / 100)}",
                color = colorResource(R.color.green_price),
                fontSize = (16 + addToFontSize).sp,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(modifier = Modifier.weight(1f))
            rating?.let { RatingBar(modifier = Modifier.padding(8.dp), rating) }
        }
    }
}