package com.nabssam.bestbook.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.nabssam.bestbook.R

@Composable
fun WriteRateReview(
    modifier: Modifier = Modifier,
    submitReview: (Int, String) -> Unit = { _, _ -> }
) {
    var starSelectedCount: Int? by remember { mutableStateOf(1) }
    var reviewText: String by remember { mutableStateOf("") }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = "Rate this book",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(5) { it ->
                IconButton(onClick = { starSelectedCount = it + 1 }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(
                            if ((starSelectedCount ?: 0) > it)
                                R.drawable.star_filled
                            else
                                R.drawable.star
                        ),
                        contentDescription = "Rating Star",
                        modifier = Modifier.size(32.dp),
                        tint = if (it < (starSelectedCount ?: 0)
                        ) colorResource(R.color.star_yellow) else MaterialTheme.colorScheme.onSurface.copy(alpha=0.4f)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        starSelectedCount?.let {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                value = reviewText,
                onValueChange = { reviewText = it },
                minLines = 3,
                label = { Text("Write a review") }
            )

            OutlinedButton(
                modifier = Modifier
                    //.align(Alignment.CenterHorizontally)
                    .padding(16.dp),
                onClick = {},
                shape = RoundedCornerShape(4.dp)
            ) {
                Text(
                    text = "Send",
                    modifier = Modifier.padding(horizontal = 24.dp)
                )
            }
        }
    }
}