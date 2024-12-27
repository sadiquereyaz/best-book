package com.imageclasses.imageclasses.ui.navigation.main

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.imageclasses.imageclasses.ui.feature.account.profile.ProfileScreen
import com.imageclasses.imageclasses.ui.feature.orderConfirmScreen.OrderStatusScreen
import com.imageclasses.imageclasses.ui.feature.orderConfirmScreen.PaymentStatusDialog
import com.imageclasses.imageclasses.ui.feature.home.HomeScreen
import com.imageclasses.imageclasses.ui.feature.quiz.QuizCategoryRoute
import com.imageclasses.imageclasses.ui.feature.subscribedEbook.SubscribedBookScreen
import com.imageclasses.imageclasses.ui.feature.subscribedQuiz.SubscribedQuizScreen
import com.imageclasses.imageclasses.ui.navigation.Route
import com.imageclasses.imageclasses.ui.util.safeNavigate


fun NavGraphBuilder.mainAppGraph(navController: NavHostController) {
    navigation<Route.MainGraph>(
        startDestination = Route.Cart()  //TODO
        //Route.Home
    ) {
        composable<Route.Home> {
            HomeScreen(
                onAllBookSelect = { navController.navigate(Route.AllBook()) },
                onNavigateToBook = { bookId: Int -> navController.navigate(route = Route.BookDetail(bookId)) },
                onAllQuizSelect = { examId: String -> navController.navigate(Route.AllBook(examId)) },
               // navigateToQuizCategory = { quizId: String -> navController.navigate(QuizCategoryRoute(quizId)) },
                navigateToQuizCategory = { quizId: String -> navController.navigate(
                    QuizCategoryRoute(quizId)
                ) },
                onBannerClick = { navController.safeNavigate("DUMMY_ROUTE") }
            )
        }

        bookGraph(navController)
        quizGraph()

        composable<Route.OrderStatus> { backStackEntry ->
            val routeObj: Route.OrderStatus = backStackEntry.toRoute()
            OrderStatusScreen(
                showPaymentStatus = {
                    navController.navigate(route = Route.PaymentDialog)

                }
            )
        }

        dialog<Route.PaymentDialog> {
            PaymentStatusDialog()
        }

        composable<Route.Profile> {
            ProfileScreen()
        }
        composable<Route.Ebook> {
            SubscribedBookScreen()
        }
        composable<Route.Quiz> {
            SubscribedQuizScreen()
        }
        settingsGraph(navController)
    }
}

