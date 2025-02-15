package com.nabssam.bestbook.presentation.navigation.main

//import com.nabssam.bestbook.presentation.ui.book.bookList.BookListScreen
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.nabssam.bestbook.presentation.navigation.Route
import com.nabssam.bestbook.presentation.ui.book.bookDetail.BookDetailScreen
import com.nabssam.bestbook.presentation.ui.book.bookDetail.ViewModelBookDetail
import com.nabssam.bestbook.presentation.ui.book.bookList.BookListScreen
import com.nabssam.bestbook.presentation.ui.book.bookList.VMBookList
import com.nabssam.bestbook.presentation.ui.cart.CartScreen
import com.nabssam.bestbook.presentation.ui.cart.VMCart

fun NavGraphBuilder.bookGraph(navController: NavHostController,) {
    composable<Route.AllBookRoute> { backStackEntry ->
       // val routeObj: Route.AllBook = backStackEntry.toRoute()
        val viewModel = hiltViewModel<VMBookList>()
        val state by viewModel.state.collectAsState()

        BookListScreen(
            state = state,
            goToDetail = { id: String, title: String ->
                navController.navigate(route = Route.BookDetailRoute(id = id, title = title))
            },
            onEvent = { viewModel.onEvent(it) }
        )

    }
    composable<Route.BookDetailRoute> { backStackEntry ->
        //val routeObj: Route.BookDetail = backStackEntry.toRoute()
        val viewModel = hiltViewModel<ViewModelBookDetail>()
        val state by viewModel.state.collectAsState()
        BookDetailScreen(
            goToCart = { navController.navigate(Route.CartRoute()) },
            state = state,
            onEvent = { viewModel.onEvent(it) },
            onSeeAllReviewClick = {
                state.fetchedBook.let  {
                    navController.navigate(
                        Route.AllReviewRoute(
                            title = it.name,
                            bookId = it.id
                        )
                    )
                }
            },
            showBooksByExam = { navController.navigate(Route.AllBookRoute(targetExam = state.fetchedBook.exam)) },
            navigateToBookDetail = { navController.navigate(Route.BookDetailRoute(id = it)) }
            //btnType = ButtonType.EBOOK,
        )
    }
    composable<Route.AllReviewRoute> { backStackEntry ->
        val routeObj: Route.BookDetailRoute = backStackEntry.toRoute()
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
                navController.navigate(route = Route.BookDetailRoute(bookId))
            },
            goToAddressScreen = {
                navController.navigate(Route.AddressRoute())
            }
        )
//        CartScreenClaude(){}
    }
}