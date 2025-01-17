package com.nabssam.bestbook.presentation.ui.book.bookDetail.composable

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.nabssam.bestbook.R
import com.nabssam.bestbook.data.remote.dto.Review
import com.nabssam.bestbook.presentation.ui.components.RatingBar

@Composable
 fun Review(
    onSeeAllReviewClick: () -> Unit,
    reviewList: List<Review>,
    modifier: Modifier
) {
    Column(modifier = modifier) {
        HorizontalDivider(thickness = 2.dp)
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
        reviewList.forEach {
            Row(modifier = Modifier.padding(0.dp, 4.dp)) {
                Box(
                    modifier = Modifier
                        .padding(start = 8.dp, top = 2.dp)
                        .clip(shape = CircleShape)
                ) {
                    AsyncImage(
                        model = it.reviewDp ?: R.drawable.profile_placeholder,
                        contentDescription = "profile pic",
                        placeholder = painterResource(id = R.drawable.profile_placeholder),
                        modifier = Modifier
                            .clip(shape = CircleShape)
                            .border(width = 1.dp, color = Color.LightGray, shape = CircleShape)
                            .height(40.dp)
                    )
                }
                Column(
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = it.username,
                            //                                modifier = Modifier.padding(horizontal = 4.dp),
                            fontWeight = FontWeight.Bold,
                            fontStyle = FontStyle.Italic
                        )
                        Spacer(Modifier.weight(1f))
                        RatingBar(
                            modifier = Modifier.height(18.dp).padding(end = 8.dp),
                            rating = it.rating,
                        )
                    }
                    Text(
                        text = it.comment,
                        modifier = Modifier.padding(top = 0.dp),
                        fontStyle = FontStyle.Italic
                    )
                }

            }
        }
    }
}