package com.nabssam.bestbook.ui.navigation.main

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.nabssam.bestbook.ui.feature.bookDetail.BookDetailScreen
import com.nabssam.bestbook.ui.feature.bookList.BookListScreen
import com.nabssam.bestbook.ui.feature.cart.CartScreen
import com.nabssam.bestbook.ui.feature.address.AddressScreen
import com.nabssam.bestbook.ui.feature.orderConfirmScreen.OrderScreen
import com.nabssam.bestbook.ui.navigation.Route

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
            goToCart = {
                navController.navigate(Route.Cart())
            }
        )
    }
    composable<Route.AllReview> { backStackEntry ->
        val routeObj: Route.BookDetail = backStackEntry.toRoute()
        BookDetailScreen(
            bookId = routeObj.bookId,
            addToCart = { TODO() },
            purchaseEbook = {},
            //btnType = ButtonType.EBOOK,
            goToCart = {
                navController.navigate(Route.Cart)
            }
        )
    }
    composable<Route.Cart> { backStackEntry ->
        //val routeObj: Route.Cart = backStackEntry.toRoute()
        CartScreen(
            goToBookDetail = { bookId: Int ->
                navController.navigate(route = Route.BookDetail(bookId))
            },
            goToAddressScreen = {
                navController.navigate(Route.Address())
            }
        )
    }
    composable<Route.Address> { backStackEntry ->
        //val routeObj: Route.Cart = backStackEntry.toRoute()
        AddressScreen (
            goToPayment = {
                navController.navigate(Route.Order())
            }
        )
    }
    composable<Route.Order> { backStackEntry ->
        //val routeObj: Route.Cart = backStackEntry.toRoute()
        OrderScreen(

        )
    }
}