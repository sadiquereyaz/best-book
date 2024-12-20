package com.imageclasses.imageclasses.ui.feature.bookDetail

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.serialization.Serializable

@Serializable
data class BookDetailRoute(val bookId: String)

@Composable
fun BookDetailScreen(
    modifier: Modifier = Modifier,
    bookId: String,
    purchaseHardCopy: (String)->Unit,
    purchaseEbook: (String) -> Unit
){

}