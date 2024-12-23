package com.imageclasses.imageclasses.ui.navigation.main

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.imageclasses.imageclasses.ui.feature.account.profile.ProfileScreen
import com.imageclasses.imageclasses.ui.feature.bookDetail.BookDetailRoute
import com.imageclasses.imageclasses.ui.feature.bookDetail.OrderStatusRoute
import com.imageclasses.imageclasses.ui.feature.bookDetail.OrderStatusScreen
import com.imageclasses.imageclasses.ui.feature.bookDetail.PaymentStatusDialog
import com.imageclasses.imageclasses.ui.feature.bookDetail.PaymentStatusRoute
import com.imageclasses.imageclasses.ui.feature.bookList.AllBookListRoute
import com.imageclasses.imageclasses.ui.feature.bookList.BookListScreen
import com.imageclasses.imageclasses.ui.feature.subscribedEbook.SubscribedBookScreen
import com.imageclasses.imageclasses.ui.feature.subscribedQuiz.SubscribedQuizScreen
import com.imageclasses.imageclasses.ui.navigation.DestinationScreen
import com.imageclasses.imageclasses.ui.feature.home.HomeScreen
import com.imageclasses.imageclasses.ui.feature.quizCategory.QuizCategoryRoute
import com.imageclasses.imageclasses.ui.feature.quizList.AllQuizListRoute
import com.imageclasses.imageclasses.ui.util.safeNavigate
import kotlinx.serialization.Serializable

@Serializable
object DummyRoute
fun NavGraphBuilder.mainAppGraph(navController: NavHostController) {
    navigation(
        route = DestinationScreen.mainApp.route,
        startDestination = DestinationScreen.home.route
    ) {
        composable(DestinationScreen.home.route) {
            HomeScreen(
                onAllBookSelect = { examId: String -> navController.navigate(AllBookListRoute(examId)) },
                onNavigateToBook = { bookId: String -> navController.navigate(route = BookDetailRoute(bookId)) },
                onAllQuizSelect = { examId: String -> navController.navigate(AllQuizListRoute(examId)) },
               // navigateToQuizCategory = { quizId: String -> navController.navigate(QuizCategoryRoute(quizId)) },
                navigateToQuizCategory = { quizId: String -> navController.navigate(QuizCategoryRoute(quizId)) },
                onBannerClick = { navController.safeNavigate("DUMMY_ROUTE") }
            )
        }

        bookGraph(navController)
        quizGraph()

        composable<OrderStatusRoute> { backStackEntry ->
            val routeObj: BookDetailRoute = backStackEntry.toRoute()
            OrderStatusScreen(
                showPaymentStatus = {
                    navController.navigate(route = PaymentStatusRoute("DUMMY_ID"))

                }
            )
        }

        dialog<PaymentStatusRoute> {
            PaymentStatusDialog()
        }

        composable(DestinationScreen.userProfile.route) {
            ProfileScreen()
        }
        composable(DestinationScreen.subscribedEbook.route) {
            SubscribedBookScreen()
        }
        composable(DestinationScreen.subscribedQuiz.route) {
            SubscribedQuizScreen()
        }

        settingsGraph(navController)
    }
}

