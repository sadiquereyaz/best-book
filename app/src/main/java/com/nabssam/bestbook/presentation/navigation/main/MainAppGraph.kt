package com.nabssam.bestbook.presentation.navigation.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.nabssam.bestbook.presentation.navigation.Route
import com.nabssam.bestbook.presentation.ui.account.profile.ProfileScreen
import com.nabssam.bestbook.presentation.ui.book.ebookList.PurchasedEbookScreen
import com.nabssam.bestbook.presentation.ui.book.ebookList.ViewModelEbook
import com.nabssam.bestbook.presentation.ui.home.HomeScreen
import com.nabssam.bestbook.presentation.ui.home.ViewModelHome
import com.nabssam.bestbook.presentation.ui.subscribedQuiz.SubscribedQuizScreen


fun NavGraphBuilder.mainAppGraph(navController: NavHostController) {
    navigation<Route.MainGraph>(
        startDestination =
//        Route.Home  //TODO
//        Route.BookDetailRoute(title = "Book Detail", id = "67973589928e04083d0c8825")   // book name: metamorphism
//            Route.AllBookRoute(targetExam = "JEE")
        Route.Ebook
//        Route.OrderGraph
//        Route.QuizGraph
//    Route.CartRoute()
    ) {
        composable<Route.Home> {
            val viewModel = hiltViewModel<ViewModelHome>()
            val state by viewModel.state.collectAsState()
            HomeScreen(
                modifier = Modifier.fillMaxSize(),
                state = state,
                onAllBookSelect = { navController.navigate(Route.AllBookRoute(targetExam = it)) },
                onNavigateToBook = { bookId ->
                    navController.navigate(Route.BookDetailRoute(bookId))
                },
                event = { event -> viewModel.onEvent(event) },
                onContestSelect = { navController.navigate(Route.QuizGraph) },
            )
        }

        composable<Route.Ebook> {
//            EbookPreviewScreen()
//            SecurePdfScreen(pdfUrl = "https://icseindia.org/document/sample.pdf")
            //PdfLibraryScreen {  }
//            PDFViewerScreen(pdfUrl = "https://icseindia.org/document/sample.pdf")
            val viewModel = hiltViewModel<ViewModelEbook>()
            PurchasedEbookScreen(
                viewModel = viewModel,
                onEvent = { viewModel.onEvent(it) }
            )
        }

        bookGraph(navController)
        orderGraph(navController)
        quizGraph(navController)

        composable<Route.Profile> {
            ProfileScreen()
        }
        /* composable<Route.Ebook> {
             RestrictScreenshot {
                 PdfViewerScreenFromUrlDirect()
             }
         }*/
        composable<Route.Notification> {
            SubscribedQuizScreen()
        }
        settingsGraph(navController)
    }
}

