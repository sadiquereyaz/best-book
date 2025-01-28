package com.nabssam.bestbook.presentation.navigation.main

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.nabssam.bestbook.presentation.navigation.Route
import com.nabssam.bestbook.presentation.ui.PdfViewerScreenFromUrlDirect
import com.nabssam.bestbook.presentation.ui.RestrictScreenshot
import com.nabssam.bestbook.presentation.ui.account.profile.ProfileScreen
import com.nabssam.bestbook.presentation.ui.home.HomeScreen
import com.nabssam.bestbook.presentation.ui.home.ViewModelHome
import com.nabssam.bestbook.presentation.ui.subscribedQuiz.SubscribedQuizScreen
import com.nabssam.bestbook.utils.safeNavigate


fun NavGraphBuilder.mainAppGraph(navController: NavHostController) {
    navigation<Route.MainGraph>(
        startDestination =
       // Route.Home  //TODO
//        Route.BookDetailRoute(title = "Book Detail", id = "book1")
//        Route.OrderGraph
//        Route.QuizGraph
    Route.CartRoute()
    ) {
        composable<Route.Home> {
            val viewModel = hiltViewModel<ViewModelHome>()
            val state by viewModel.state.collectAsState()
            HomeScreen(
                state = state,
                onAllBookSelect = { navController.navigate(Route.AllBookRoute(targetExam = it)) },
                onNavigateToBook = { bookId ->
                    navController.navigate(Route.BookDetailRoute(bookId))
                },
                onAllQuizSelect = { examId: String ->

                },
                // navigateToQuizCategory = { quizId: String -> navController.navigate(QuizCategoryRoute(quizId)) },
                navigateToQuiz = { quizId ->
                    navController.navigate(
                        Route.QuizSubjectRoute()
                    )
                },
                onBannerClick = { navController.safeNavigate("DUMMY_ROUTE") },
                event = { event -> viewModel.onEvent(event) },
                //onQuizSelect = {navController.navigate(Route.QuizCategoryRoute(it))},
                onContestSelect = {navController.navigate(Route.QuizGraph)},
            )
        }

        bookGraph(navController)
        orderGraph(navController)
        quizGraph(navController)

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

