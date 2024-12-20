package com.imageclasses.imageclasses.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.Text

import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.imageclasses.imageclasses.navigation.DestinationScreen
import com.imageclasses.imageclasses.ui.components.ImageClassesTitle
import com.imageclasses.imageclasses.ui.feature.account.profile.ProfileScreen
import com.imageclasses.imageclasses.ui.feature.account.auth.AuthScreen
import com.imageclasses.imageclasses.ui.feature.bookDetail.BookDetailRoute
import com.imageclasses.imageclasses.ui.feature.bookDetail.BookDetailScreen
import com.imageclasses.imageclasses.ui.feature.bookDetail.OrderStatusRoute
import com.imageclasses.imageclasses.ui.feature.bookDetail.OrderStatusScreen
import com.imageclasses.imageclasses.ui.feature.bookDetail.PaymentStatusDialog
import com.imageclasses.imageclasses.ui.feature.bookDetail.PaymentStatusRoute
import com.imageclasses.imageclasses.ui.feature.bookList.BookListRoute
import com.imageclasses.imageclasses.ui.feature.bookList.BookListScreen
import com.imageclasses.imageclasses.ui.feature.subscribedEbook.SubscribedBookScreen
import com.imageclasses.imageclasses.ui.feature.subscribedQuiz.SubscribedQuizScreen
import com.treeto.treeto.ui.feature.home.HomeScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController: NavHostController = rememberNavController()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    if (true) ImageClassesTitle()
                    else Text(
                        text = "Heading",
                        modifier = Modifier,
                        fontWeight = FontWeight.Bold,
//                        fontSize = 28.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                scrollBehavior = scrollBehavior
            )
        },
        bottomBar = {
            BottomNavigationMenu(
                navController = navController
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* do something */ },
                containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
            ) {
                Icon(Icons.Filled.ShoppingCart, "shopping")
            }
        }
    ) { innerPadding ->
        val isLoggedIn = isLoggedIn()
        LaunchedEffect(isLoggedIn) {
            if (!isLoggedIn) {
                navController.navigate(DestinationScreen.auth.route) {
                    popUpTo(DestinationScreen.auth.route) { inclusive = true }
                }
            }
        }
        NavHost(
            navController = navController,
            startDestination = if (isLoggedIn) DestinationScreen.mainApp.route else DestinationScreen.ots.route,
            modifier = modifier.padding(innerPadding)
        ) {
            navigation(
                route = DestinationScreen.ots.route,
                startDestination = DestinationScreen.intro.route
            ) {
                composable(route = DestinationScreen.intro.route) {
                    //IntroScreen()
                }
                composable(route = DestinationScreen.auth.route) {
                    AuthScreen()
                }
            }
            // nested navigation
            navigation(
                route = DestinationScreen.mainApp.route,
                startDestination = DestinationScreen.home.route
            ) {
                composable(DestinationScreen.home.route) {
                    HomeScreen(
                        onAllBookSelect = { targetExam: String ->
                            navController.navigate(
                                DestinationScreen.home.createRoute(
                                    targetExam
                                )
                            )
                        },
                        onNavigateToBook = { bookId: String ->
                            navController.navigate(route = BookDetailRoute(bookId))
                        },
                    )
                }
                composable<BookListRoute>{ backStackEntry->
                    val routeObj: BookListRoute = backStackEntry.toRoute()
                    BookListScreen(
                        booksPreference = routeObj.targetExamId,
                        onNavigateToBook = { bookId:String ->
                            navController.navigate(route = BookDetailRoute(bookId))
                        },
                    )
                }
                composable<BookDetailRoute>{ backStackEntry->
                    val routeObj: BookDetailRoute = backStackEntry.toRoute()
                    BookDetailScreen(
                        bookId = routeObj.bookId,
                        purchaseHardCopy = { TODO() },
                        purchaseEbook = { TODO() },
                    )
                }
                composable<OrderStatusRoute>{ backStackEntry->
                    val routeObj: BookDetailRoute = backStackEntry.toRoute()
                    OrderStatusScreen(
                        showPaymentStatus = {
                            navController.navigate(route = PaymentStatusRoute("DUMMY_ID"))

                        }
                    )
                }
                dialog<PaymentStatusRoute>{
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
            }
        }
    }
}

fun isLoggedIn(): Boolean {

    return true

}
