package com.nabssam.bestbook.presentation.navigation.main

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.nabssam.bestbook.presentation.ui.book.bookDetail.BookDetailScreen
import com.nabssam.bestbook.presentation.ui.book.bookList.BookListScreen
import com.nabssam.bestbook.presentation.ui.cart.CartScreen
import com.nabssam.bestbook.presentation.ui.address.AddressScreen
import com.nabssam.bestbook.presentation.ui.book.bookDetail.ViewModelBookDetail
import com.nabssam.bestbook.presentation.ui.book.bookList.ViewModelBookList
import com.nabssam.bestbook.presentation.ui.orderConfirmScreen.OrderScreen
import com.nabssam.bestbook.presentation.navigation.Route

fun NavGraphBuilder.bookGraph(navController: NavHostController,) {

    // Shared ViewModel scoped to the book navigation graph
//    val sharedViewModel = hiltViewModel<ViewModelBookList>()

    composable<Route.AllBook> { backStackEntry ->
       // val routeObj: Route.AllBook = backStackEntry.toRoute()
        val viewModel = hiltViewModel<ViewModelBookList>()
        val state by viewModel.state.collectAsState()
        BookListScreen(
            state = state,
            onNavigateToBook = { bookId: Int ->
                navController.navigate(route = Route.BookDetail(bookId))
            },
        )
    }
    composable<Route.BookDetail> { backStackEntry ->
        //val routeObj: Route.BookDetail = backStackEntry.toRoute()
        val viewModel = hiltViewModel<ViewModelBookDetail>()
        val state by viewModel.state.collectAsState()
        BookDetailScreen(
            state  = state,
            //bookId = routeObj.bookId,
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
        /*BookDetailScreen(
            bookId = routeObj.bookId,
            addToCart = { TODO() },
            purchaseEbook = {},
            //btnType = ButtonType.EBOOK,
            goToCart = {
                navController.navigate(Route.Cart)
            },
            state = state
        )*/
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