package com.nabssam.bestbook.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nabssam.bestbook.R

@Composable
fun ImageClassesTitle(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.pngsqonlylogo), "logo",
            modifier = Modifier.size(42.dp)
        )
        Text(
            text = "Image Classes",
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
            color = MaterialTheme.colorScheme.scrim
        )
        /*  buildAnnotatedString {
              withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary, fontSize = 32.sp)) {
                  append("Image")
              }
              append("Classes ")
          }*/
    }
}