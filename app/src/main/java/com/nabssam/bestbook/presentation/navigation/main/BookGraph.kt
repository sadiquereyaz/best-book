package com.nabssam.bestbook.presentation.navigation.main

//import com.nabssam.bestbook.presentation.ui.book.bookList.BookListScreen
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.nabssam.bestbook.presentation.navigation.Route
import com.nabssam.bestbook.presentation.ui.book.all_review.AllReviewScreen
import com.nabssam.bestbook.presentation.ui.book.all_review.AllReviewViewModel
import com.nabssam.bestbook.presentation.ui.book.bookDetail.BookDetailScreen
import com.nabssam.bestbook.presentation.ui.book.bookDetail.EventBookDetail
import com.nabssam.bestbook.presentation.ui.book.bookDetail.ViewModelBookDetail
import com.nabssam.bestbook.presentation.ui.book.bookList.BookListScreen
import com.nabssam.bestbook.presentation.ui.book.bookList.VMBookList
import com.nabssam.bestbook.presentation.ui.cart.CartScreen
import com.nabssam.bestbook.presentation.ui.cart.VMCart

fun NavGraphBuilder.bookGraph(navController: NavHostController) {

    composable<Route.AllBookRoute> { backStackEntry ->
        // val routeObj: Route.AllBook = backStackEntry.toRoute()
        val viewModel = hiltViewModel<VMBookList>()
        val state by viewModel.state.collectAsState()

        BookListScreen(
            modifier = Modifier.fillMaxSize(),
            state = state,
            goToDetail = { id: String, title: String ->
                navController.navigate(route = Route.BookDetailRoute(id = id, title = title))
            },
            onEvent = { viewModel.onEvent(it) }
        )
    }

    composable<Route.BookDetailRoute> { backStackEntry ->
        val routeObj: Route.BookDetailRoute = backStackEntry.toRoute()
        val viewModel: ViewModelBookDetail = hiltViewModel()
        val uiState by viewModel.uiState.collectAsState()
        BookDetailScreen(
            modifier = Modifier,
            goToCart = { navController.navigate(Route.CartRoute()) },
            state = uiState,
            onEvent = { viewModel.onEvent(it) },
            onSeeAllReviewClick = { navController.navigate(Route.AllReviewRoute(bookId = routeObj.id)) },
            showBooksByExam = {
                navController.navigate(
                    Route.AllBookRoute(
                        targetExam = uiState.fetchedBook.exam ?: "all"
                    )
                )
            },
            navigateToBookDetail = { navController.navigate(Route.BookDetailRoute(id = it)) },
            submitReview = {
                viewModel.onEvent(
                    EventBookDetail.SubmitReview
                )
            },
            deleteReview = { viewModel.onEvent(EventBookDetail.DeleteReview(it)) },
            updateRateReview = { rate, review ->
                viewModel.onEvent(
                    EventBookDetail.UpdateRateReview(
                        rate = rate,
                        review = review
                    )
                )
            }
        )
    }

    composable<Route.AllReviewRoute> {
        val viewModel: AllReviewViewModel = hiltViewModel()
        val uiState by viewModel.uiState.collectAsState()
        AllReviewScreen(uiState = uiState)
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