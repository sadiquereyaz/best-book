package com.nabssam.bestbook.presentation.ui.home.components

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.nabssam.bestbook.R
import com.nabssam.bestbook.domain.model.Book
import com.nabssam.bestbook.presentation.ui.components.ErrorScreen
import com.nabssam.bestbook.presentation.ui.components.FullScreenProgressIndicator

@Composable
 fun HorizontalBookList(
    modifier: Modifier = Modifier,
    loading : Boolean = true,
    bookList: List<Book>,
    error: String? = null,
    onNavigateToBook: (String) -> Unit,
    retry: () -> Unit
) {
    Box(modifier = modifier){
        if (loading) {
            FullScreenProgressIndicator(modifier = Modifier.height(dimensionResource(R.dimen.book_height_home)))
        } else if (error != null) {
            ErrorScreen(
                message = error,
                modifier = Modifier.height(dimensionResource(R.dimen.book_height_home)),
                onRetry = retry
            )
        } else {
            LazyRow(
                modifier = Modifier,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(bookList) {
                    Box(
                        modifier = Modifier
                            .clickable { onNavigateToBook(it.id) }
                            .border(
                                width = 0.5.dp,
                                shape = RoundedCornerShape(6.dp),
                                color = Color.Black
                            )
                        //  .clip(shape = RoundedCornerShape(6.dp))
                        //.padding(6.dp)
                    ) {
                        AsyncImage(
                            model = it.coverUrl,
                            contentDescription = null,
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier
                                .width(dimensionResource(R.dimen.book_width_home))
                                .height(dimensionResource(R.dimen.book_height_home))
                                .clip(shape = RoundedCornerShape(6.dp)),
                        )
                    }
                }
            }
        }
    }
}
