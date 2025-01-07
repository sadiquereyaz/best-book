package com.nabssam.bestbook.presentation.navigation.main

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.navigation
import com.nabssam.bestbook.presentation.ui.account.profile.ProfileScreen
import com.nabssam.bestbook.presentation.ui.PdfViewerScreenFromUrlDirect
import com.nabssam.bestbook.presentation.ui.RestrictScreenshot
import com.nabssam.bestbook.presentation.ui.orderConfirmScreen.PaymentStatusDialog
import com.nabssam.bestbook.presentation.ui.subscribedQuiz.SubscribedQuizScreen
import com.nabssam.bestbook.presentation.navigation.Route
import com.nabssam.bestbook.presentation.ui.productdetail.ProductListScreen
import com.nabssam.bestbook.presentation.ui.productlist.ProductListViewModel


fun NavGraphBuilder.mainAppGraph(navController: NavHostController) {
    navigation<Route.MainGraph>(
        startDestination = Route.Home  //TODO
        //Route.Home
    ) {
        composable<Route.Home> {
            /*val viewModel = hiltViewModel<ViewModelHome>()
            val state by viewModel.state.collectAsState()
            HomeScreen(
                state = state,
                onAllBookSelect = { navController.navigate(Route.AllBook()) },
                onNavigateToBook = { bookId: Int ->
                    navController.navigate(Route.BookDetail(bookId))
                },
                onAllQuizSelect = { examId: String -> navController.navigate(Route.AllBook(examId)) },
                // navigateToQuizCategory = { quizId: String -> navController.navigate(QuizCategoryRoute(quizId)) },
                navigateToQuiz = { quizId: Int ->
                    navController.navigate(
                        Route.QuizSubjectRoute()
                    )
                },
                onBannerClick = { navController.safeNavigate("DUMMY_ROUTE") },
                //onQuizSelect = {navController.navigate(Route.QuizCategoryRoute(it))}
            )*/

            val viewModel = hiltViewModel<ProductListViewModel>()
            val state by viewModel.uiState.collectAsState()
            ProductListScreen(
                viewModel = viewModel,
                navigateToProductDetails = { id: String, title: String ->
                    navController.navigate(route = Route.ProductDetailRoute(id = id, title = title))
                },
            )
        }

        bookGraph(navController)

        quizGraph(navController)

        dialog<Route.PaymentDialog> {
            PaymentStatusDialog()
        }

        composable<Route.Profile> {
            ProfileScreen()
        }
        composable<Route.Ebook> {
            RestrictScreenshot {
                PdfViewerScreenFromUrlDirect()
            }

        }
        composable<Route.SubscribedQuiz> {
            SubscribedQuizScreen()
        }
        settingsGraph(navController)
    }
}

