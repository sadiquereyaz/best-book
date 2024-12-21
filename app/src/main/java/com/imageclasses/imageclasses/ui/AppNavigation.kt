package com.imageclasses.imageclasses.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.imageclasses.imageclasses.auth.FirebaseAuth
import com.imageclasses.imageclasses.navigation.DestinationScreen
import com.imageclasses.imageclasses.ui.feature.account.auth.SignIn
import com.imageclasses.imageclasses.ui.feature.account.profile.ProfileScreen

import com.imageclasses.imageclasses.ui.feature.account.auth.SignUp
import com.imageclasses.imageclasses.ui.feature.bookList.BookListScreen
import com.treeto.treeto.ui.feature.home.HomeScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController: NavHostController = rememberNavController()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row {
                        Text(text = "Image Classes")
                        Spacer(Modifier.weight(1f))
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = "Notifications",
                            modifier = Modifier.padding(end = 16.dp)
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomNavigationMenu(
                selectedItem = BottomNavigationItem.HOME,
                navController = navController
            )
        }
    ) { innerPadding ->
        val isLoggedIn = isLoggedIn()
        LaunchedEffect(isLoggedIn) {
            if (!isLoggedIn) {
                navController.navigate(DestinationScreen.auth_signup.route){
                    popUpTo(DestinationScreen.auth_signup.route) { inclusive = true }
                }
            }
        }
        NavHost(
            navController = navController,
            startDestination = DestinationScreen.ots.route,
            modifier = modifier.padding(innerPadding)
        ) {
            navigation(
                route = DestinationScreen.ots.route,
                startDestination = DestinationScreen.auth_signup.route
            ){
                composable(route = DestinationScreen.intro.route) {
                    //IntroScreen()
                }
                composable(route = DestinationScreen.auth_signup.route) {
                    SignUp(navController = navController)
                }
                composable(route = DestinationScreen.auth_signin.route) {
                    SignIn(navController = navController)
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
                        onNavigateToBook = { bookId: Int ->
                            navController.navigate(DestinationScreen.bookList.createRoute(bookId))
                        },
                    )
                }
                composable(DestinationScreen.bookList.route) {
                    val targetExam = it.arguments?.getString("targetExam")
                    BookListScreen(
                        booksPreference = targetExam,
                        onNavigateToBook = { bookId ->
                            navController.navigate(DestinationScreen.bookList.createRoute(bookId))
                        },
                    )
                }
                composable(DestinationScreen.userProfile.route) {
                    ProfileScreen()
                }
            }
        }
    }
}

fun isLoggedIn(): Boolean {

val firebaseAuth = FirebaseAuth()
    return  firebaseAuth.isUserAlreadyLoggedIn()

}
