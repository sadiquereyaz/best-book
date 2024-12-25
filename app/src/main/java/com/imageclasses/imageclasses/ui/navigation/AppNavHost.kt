package com.imageclasses.imageclasses.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.imageclasses.imageclasses.auth.FirebaseAuth
import com.imageclasses.imageclasses.ui.navigation.auth.authGraph
import com.imageclasses.imageclasses.ui.navigation.main.mainAppGraph


@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier,
    innerPadding: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = AppNavigation(firebaseAuth = FirebaseAuth()).getStartDestination(),
        modifier = modifier.padding(innerPadding)
    ) {
        authGraph(navController)
        mainAppGraph(navController)
    }
}

//Dependency Injection (preferred)
class AppNavigation(private val firebaseAuth: FirebaseAuth) {
    fun getStartDestination(): Route {
        val isLoggedIn = firebaseAuth.isUserAlreadyLoggedIn()
        return if (isLoggedIn) Route.MainGraph else Route.AuthGraph
    }
}
