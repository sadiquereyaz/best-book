package com.nabssam.bestbook.presentation.ui.productdetail


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nabssam.bestbook.domain.model.Book
import com.nabssam.bestbook.presentation.ui.book.bookList.BookCoverImage
import com.nabssam.bestbook.presentation.ui.components.BookTitlePrice
import com.nabssam.bestbook.presentation.ui.components.ErrorScreen
import com.nabssam.bestbook.presentation.ui.components.FullScreenProgressIndicator
import com.nabssam.bestbook.presentation.ui.productlist.ProductListViewModel
import com.nabssam.bestbook.utils.Resource

@Composable
fun ProductListScreen(
    modifier: Modifier = Modifier.fillMaxSize(),
    viewModel: ProductListViewModel,
    navigateToProductDetails: (String, String) -> Unit,
) {
    val state = viewModel.uiState.collectAsState()
    /*LaunchedEffect(key1 = productId) {
        viewModel.fetchProductDetails(productId)
    }*/

    when (state.value) {
        is Resource.Loading -> {
            // Show loading indicator
            FullScreenProgressIndicator(modifier = modifier)
        }

        is Resource.Success -> {
            //LazyColumn (list){
            /*LazyColumn(modifier = modifier) {
                (state.value as Resource.Success<List<Product>>).data?.let {
                    items(it) {
                       Card(modifier = Modifier.fillMaxWidth().padding(8.dp)) { Text(it.name) }
                    }
                }
            }*/
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = modifier
                    .fillMaxSize()
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                (state.value as Resource.Success<List<Book>>).data?.let {
                    items(it) {
                        Box(
                            modifier = Modifier
                                .clickable { navigateToProductDetails(it.id, it.name) },
                            contentAlignment = Alignment.TopCenter
                        ) {
                            Column {
                                BookCoverImage(coverImage = it.imageUrls[0])
                                //book title and price
                                BookTitlePrice(
                                    originalPrice = it.price,
                                    title = it.name
                                )
                            }
                        }
                    }

                }
            }
        }


        is Resource.Error -> {
            val error =  (state.value as Resource.Error<List<Book>>).message ?: "An Error Occurred"
            ErrorScreen(
                message = error,
                modifier = modifier,
                onRetry = { viewModel.retry() }
            )
        }
    }
}