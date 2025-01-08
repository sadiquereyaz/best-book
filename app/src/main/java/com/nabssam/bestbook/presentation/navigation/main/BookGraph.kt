package com.nabssam.bestbook.presentation.navigation.main

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.nabssam.bestbook.presentation.ui.book.bookDetail.ProductDetailScreen
//import com.nabssam.bestbook.presentation.ui.book.bookList.BookListScreen
import com.nabssam.bestbook.presentation.ui.cart.CartScreen
import com.nabssam.bestbook.presentation.ui.address.AddressScreen
import com.nabssam.bestbook.presentation.ui.book.bookDetail.ViewModelBookDetail
import com.nabssam.bestbook.presentation.ui.book.bookList.ViewModelBookList
import com.nabssam.bestbook.presentation.ui.orderConfirmScreen.OrderScreen
import com.nabssam.bestbook.presentation.navigation.Route
import com.nabssam.bestbook.presentation.ui.cart.VMCart
import com.nabssam.bestbook.presentation.ui.productdetail.ProductListScreen
import com.nabssam.bestbook.presentation.ui.productlist.ProductListViewModel

fun NavGraphBuilder.bookGraph(navController: NavHostController,) {

    // Shared ViewModel scoped to the book navigation graph
//    val sharedViewModel = hiltViewModel<ViewModelBookList>()

    composable<Route.AllBookRoute> { backStackEntry ->
       // val routeObj: Route.AllBook = backStackEntry.toRoute()
        val viewModel = hiltViewModel<ProductListViewModel>()
        ProductListScreen(
            viewModel = viewModel,
            navigateToProductDetails = { id: String, title: String ->
                navController.navigate(route = Route.ProductDetailRoute(id = id, title = title))
            },
        )
       /* val viewModel = hiltViewModel<ViewModelBookList>()
        val state by viewModel.state.collectAsState()
        BookListScreen(
            state = state,
            onNavigateToBook = { bookId: String ->
                navController.navigate(route = Route.ProductDetailRoute(bookId))
            },
        )*/

    }
    composable<Route.ProductDetailRoute> { backStackEntry ->
        //val routeObj: Route.BookDetail = backStackEntry.toRoute()
        val viewModel = hiltViewModel<ViewModelBookDetail>()
        ProductDetailScreen(
            vm = viewModel,
            //bookId = routeObj.bookId,
            purchaseEbook = {},
            goToCart = {
                navController.navigate(Route.CartRoute())
            },
            //btnType = ButtonType.EBOOK,
        )
    }
    composable<Route.AllReviewRoute> { backStackEntry ->
        val routeObj: Route.ProductDetailRoute = backStackEntry.toRoute()
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
    composable<Route.CartRoute> { backStackEntry ->
        //val routeObj: Route.Cart = backStackEntry.toRoute()
        val vm = hiltViewModel<VMCart>()
        CartScreen(
            vm = vm,
            goToBookDetail = { bookId: String ->
                navController.navigate(route = Route.ProductDetailRoute(bookId))
            },
            goToAddressScreen = {
                navController.navigate(Route.AddressRoute())
            }
        )
    }
    composable<Route.AddressRoute> { backStackEntry ->
        //val routeObj: Route.Cart = backStackEntry.toRoute()
        AddressScreen (
            goToPayment = {
                navController.navigate(Route.OrderRoute())
            }
        )
    }
    composable<Route.OrderRoute> { backStackEntry ->
        //val routeObj: Route.Cart = backStackEntry.toRoute()
        OrderScreen(

        )
    }
}