package com.nabssam.bestbook.presentation.ui.book.bookDetail.composable

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.nabssam.bestbook.R
import com.nabssam.bestbook.domain.model.Review
import com.nabssam.bestbook.presentation.ui.components.HorizontalSpacer
import com.nabssam.bestbook.presentation.ui.components.RatingBar

@Composable
fun ReviewItem(
    review: Review,
    modifier: Modifier = Modifier,
    onDeleteReview: (() -> Unit)? = null
) {
    Column {
        Row(modifier = modifier.padding(horizontal = 8.dp)) {
            // profile pic
            Box(
                modifier = Modifier
                    .padding(top = 4.dp)
                    .clip(shape = CircleShape)
            ) {
                AsyncImage(
                    model = review.profilePicture ?: R.drawable.profile_placeholder,
                    contentDescription = "profile pic",
                    placeholder = painterResource(id = R.drawable.profile_placeholder),
                    modifier = Modifier
                        .clip(shape = CircleShape)
                        .border(width = 1.dp, color = Color.LightGray, shape = CircleShape)
                        .height(40.dp)
                        .offset(y = 4.dp)
                )
            }
            HorizontalSpacer(8)

            // name, star and date
            Column(
                modifier = Modifier.padding(/*start = 8.dp*/)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = review.username,
                        modifier = Modifier.padding(start = 4.dp),
                        fontWeight = FontWeight.Bold,
                        //fontStyle = FontStyle.Italic
                    )
                    Spacer(Modifier.weight(1f))
                    Text(text = review.date, fontSize = 12.sp)

                    // Three-dot menu
                    onDeleteReview?.let {
                        ThreeDotMenu(
                            onDeleteClick = it
                        )
                    }
                }

                RatingBar(
                    modifier = Modifier
                        .height(18.dp),
                    rating = review.rate,
                )
            }
        }
        // review text and description
        review.description?.let {
            Text(
                text = it,
                modifier = Modifier.padding(16.dp, 4.dp, 16.dp),
                fontStyle = FontStyle.Italic,
            )
        }
        HorizontalDivider(
            Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
        )
    }
}

@Composable
fun ThreeDotMenu(
    onDeleteClick: (() -> Unit)
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        Icon(
            modifier = Modifier
                .clip(CircleShape)
                .clickable { expanded = true },
            imageVector = Icons.Default.MoreVert,
            contentDescription = "More options"
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("Delete") },
                onClick = {
                    onDeleteClick()
                    expanded = false
                }
            )
        }
    }
}