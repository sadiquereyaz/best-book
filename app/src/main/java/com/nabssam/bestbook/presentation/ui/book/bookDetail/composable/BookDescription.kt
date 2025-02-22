package com.nabssam.bestbook.presentation.ui.book.bookDetail.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
 fun BookDescription(desc: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
      /*  HorizontalDivider(
            thickness = 2.dp,
            modifier = Modifier.padding(bottom = 4.dp)
        )*/
        Text(
            text = "Description",
            modifier = Modifier
                .padding(start = 8.dp, top = 4.dp, bottom = 4.dp),
            fontWeight = FontWeight.Bold
        )
        Text(
            text = desc,
            modifier = Modifier.padding(start = 8.dp, end = 8.dp)
        )
    }
}