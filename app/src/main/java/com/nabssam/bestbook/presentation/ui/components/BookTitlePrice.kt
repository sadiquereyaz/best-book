package com.nabssam.bestbook.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BookTitlePrice(
    modifier: Modifier = Modifier,
    addToFontSize: Int = 0, padTop: Dp = 8.dp,
    maxLine: Int = 2,
    discPer: Int = 30,
    originalPrice: Int = 560,
    title: String = "AMU Booster"
) {
    Column  (modifier = modifier){

        //title
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
                text = "₹${(originalPrice - (discPer * originalPrice) / 100) }",
                fontSize = (16 + addToFontSize).sp,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )

        }
    }
}