package com.imageclasses.imageclasses.ui.navigation.main

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.imageclasses.imageclasses.ui.feature.bookDetail.BookDetailScreen
import com.imageclasses.imageclasses.ui.feature.bookList.BookListScreen
import com.imageclasses.imageclasses.ui.navigation.Route

fun NavGraphBuilder.bookGraph(navController: NavHostController) {
    composable<Route.AllBook> { backStackEntry ->
       // val routeObj: Route.AllBook = backStackEntry.toRoute()
        BookListScreen(
            onNavigateToBook = { bookId: Int ->
                navController.navigate(route = Route.BookDetail(bookId))
            },
        )
    }
    composable<Route.BookDetail> { backStackEntry ->
        val routeObj: Route.BookDetail = backStackEntry.toRoute()
        BookDetailScreen(
            bookId = routeObj.bookId,
            addToCart = { TODO() },
            purchaseEbook = {},
            //btnType = ButtonType.EBOOK,
            goToCart = {}
        )
    }
}