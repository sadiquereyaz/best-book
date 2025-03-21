package com.nabssam.bestbook.presentation.ui.book.bookDetail.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ReviewDetailScreen(
    onSeeAllReviewClick: () -> Unit,
    reviewList: List<com.nabssam.bestbook.domain.model.Review>,
    modifier: Modifier
) {
    Column(modifier = modifier) {
        HorizontalDivider(thickness = 2.dp, modifier = Modifier.padding(bottom = 8.dp, top = 16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Reviews",
                modifier = Modifier
                    .padding(start = 8.dp, top = 4.dp, bottom = 4.dp),
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "See all",
                modifier = Modifier
                    .padding(end = 8.dp)
                    .clip(RoundedCornerShape(50))
                    .clickable { onSeeAllReviewClick() }
                    .padding(4.dp),
                textDecoration = TextDecoration.Underline,
                fontSize = 14.sp
            )
        }
        // reviews
        reviewList.forEach { review ->
            ReviewItem(review, Modifier)
        }
    }
}