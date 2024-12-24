package com.imageclasses.imageclasses.ui.navigation.main

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.imageclasses.imageclasses.ui.feature.bookDetail.BookDetailRoute
import com.imageclasses.imageclasses.ui.feature.bookDetail.BookDetailScreen
import com.imageclasses.imageclasses.ui.feature.bookDetail.ButtonType
import com.imageclasses.imageclasses.ui.feature.bookList.AllBookListRoute
import com.imageclasses.imageclasses.ui.feature.bookList.BookListScreen

fun NavGraphBuilder.bookGraph(navController: NavHostController) {
    composable<AllBookListRoute> { backStackEntry ->
        val routeObj: AllBookListRoute = backStackEntry.toRoute()
        BookListScreen(
            examId = routeObj.targetExamId,
            onNavigateToBook = { bookId: String ->
                navController.navigate(route = BookDetailRoute(bookId))
            },
        )
    }
    composable<BookDetailRoute> { backStackEntry ->
        val routeObj: BookDetailRoute = backStackEntry.toRoute()
        BookDetailScreen(
            bookId = routeObj.bookId,
            addToCart = { TODO() },
            purchaseEbook = {},
            //btnType = ButtonType.EBOOK,
            goToCart = {}
        )
    }
}