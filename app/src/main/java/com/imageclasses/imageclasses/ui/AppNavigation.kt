package com.imageclasses.imageclasses.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember

import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.imageclasses.imageclasses.navigation.DestinationScreen
import com.imageclasses.imageclasses.ui.components.ImageClassesTitle
import com.imageclasses.imageclasses.ui.feature.account.profile.ProfileScreen
import com.imageclasses.imageclasses.ui.feature.account.auth.AuthScreen
import com.imageclasses.imageclasses.ui.feature.bookList.BookListScreen
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
                selectedItem = BottomNavigationItem.HOME,
                navController = navController
            )


        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* do something */ },
                containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
            ) {
                Icon(Icons.Filled.ShoppingCart, "Localized description")
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

    return true

}
