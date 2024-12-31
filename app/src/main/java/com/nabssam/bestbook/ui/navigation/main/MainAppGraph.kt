package com.nabssam.bestbook.ui.navigation.main

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.navigation
import com.nabssam.bestbook.ui.feature.account.profile.ProfileScreen
import com.nabssam.bestbook.ui.feature.bookList.PdfViewerScreenFromUrlDirect
import com.nabssam.bestbook.ui.feature.bookList.RestrictScreenshot
import com.nabssam.bestbook.ui.feature.home.HomeScreen
import com.nabssam.bestbook.ui.feature.orderConfirmScreen.PaymentStatusDialog
import com.nabssam.bestbook.ui.feature.subscribedQuiz.SubscribedQuizScreen
import com.nabssam.bestbook.ui.navigation.Route
import com.nabssam.bestbook.ui.util.safeNavigate


fun NavGraphBuilder.mainAppGraph(navController: NavHostController) {
    navigation<Route.MainGraph>(
        startDestination = Route.Home  //TODO
        //Route.Home
    ) {
        composable<Route.Home> {
            HomeScreen(
                onAllBookSelect = { navController.navigate(Route.AllBook()) },
                onNavigateToBook = { bookId: Int -> navController.navigate(route = Route.BookDetail(bookId)) },
                onAllQuizSelect = { examId: String -> navController.navigate(Route.AllBook(examId)) },
               // navigateToQuizCategory = { quizId: String -> navController.navigate(QuizCategoryRoute(quizId)) },
                navigateToQuiz = { quizId: Int -> navController.navigate(
                    Route.QuizSubjectRoute()
                ) },
                onBannerClick = { navController.safeNavigate("DUMMY_ROUTE") },
                //onQuizSelect = {navController.navigate(Route.QuizCategoryRoute(it))}
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

