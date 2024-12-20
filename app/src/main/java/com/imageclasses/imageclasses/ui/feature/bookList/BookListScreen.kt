package com.imageclasses.imageclasses.ui.feature.bookList

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.serialization.Serializable

/*
If you need to pass data to a destination, define the route with a class that has parameters.
* Whenever you need to pass arguments to that destination,
* you create an instance of your route class,
* passing the arguments to the class constructor.
*/
@Serializable
data class BookListRoute(val targetExamId: String? = null)

//For optional arguments, create nullable fields with a default value.
@Serializable
data class ProfileRoute(val userId: String? = null)

@Serializable
object BookScreenRoute

@Composable
fun BookListScreen(
    modifier: Modifier = Modifier,
    booksPreference: String?,
    onNavigateToBook: (String) -> Unit
){

}