package com.nabssam.bestbook.presentation.ui.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nabssam.bestbook.R
import com.nabssam.bestbook.domain.model.Book
import com.nabssam.bestbook.presentation.ui.components.gradientBrush
import com.nabssam.bestbook.utils.BestBookPreview

@Composable
fun PyqRow(
    modifier: Modifier = Modifier,
    navigateToPyq: (String) -> Unit,
    pyqList: List<Book>,
) {
    LazyRow(
        modifier = modifier/*.height(100.dp)*/,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(
            pyqList
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = modifier
                    .clip(RoundedCornerShape(8.dp))
                    .clickable { navigateToPyq(it.id) }
                    .border(
                        width = 1.dp,
                        shape = RoundedCornerShape(8.dp),
                        brush = gradientBrush(),
                    )
                    .padding(4.dp)
                    .size(
                        width = dimensionResource(R.dimen.book_width_home),
                        height = TextFieldDefaults.MinHeight
                    )
            ) {
                Text(
                    text = it.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

    }
}

@BestBookPreview
@Composable
fun QuizRowPreview() {
    //PyqRow(modifier = Modifier, {}, {}, state.fetchedPyq)
}
