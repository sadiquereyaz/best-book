package com.nabssam.bestbook.presentation.ui.book.bookDetail.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nabssam.bestbook.presentation.ui.book.bookDetail.StateBookDetail
import com.nabssam.bestbook.presentation.ui.home.components.HorizontalBookList

@Composable
 fun RelatedBookList(
    modifier: Modifier = Modifier,
    state: StateBookDetail,
    onNavigateToBook: (String) -> Unit
) {
    HorizontalDivider(
        thickness = 2.dp,
        modifier = Modifier.padding(bottom = 4.dp, top = 8.dp)
    )
    Column(modifier = modifier) {

        Text(
            text = "Related",
            modifier = Modifier
                .padding(start = 4.dp, top = 4.dp, bottom = 8.dp),
            fontWeight = FontWeight.Bold
        )
        HorizontalBookList(
            loading = state.isListFetching,
            bookList = state.fetchedList,
            error = state.listError,
            onNavigateToBook = onNavigateToBook
        ) { }
    }
}