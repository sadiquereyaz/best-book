package com.nabssam.bestbook.presentation.ui.home.components

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.nabssam.bestbook.R
import com.nabssam.bestbook.presentation.ui.home.StateHomeScreen

@Composable
 fun HomeBookList(
    state: StateHomeScreen,
    onNavigateToBook: (String) -> Unit
) {
    LazyRow(
        modifier = Modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(state.fetchedBooks) {
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
                    //painter = painterResource(id = R.drawable.book1),
                    model = it.imageUrls[0],
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
