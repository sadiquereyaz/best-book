package com.nabssam.bestbook.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.nabssam.bestbook.data.connectivity.NetworkConnectivityObserver
import com.nabssam.bestbook.utils.FirebaseAuth
import com.nabssam.bestbook.presentation.navigation.auth.authGraph
import com.nabssam.bestbook.presentation.navigation.main.mainAppGraph
import com.nabssam.bestbook.presentation.ui.components.NetworkSnackbar


@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier,
    innerPadding: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = AppNavigation(firebaseAuth = FirebaseAuth()).getStartDestination(),
        modifier = modifier.padding(innerPadding),
    ) {

        // registration nested graph
        authGraph(navController)

        // main app nested graph
        mainAppGraph(navController)
    }
}

//Dependency Injection (preferred)
class AppNavigation(private val firebaseAuth: FirebaseAuth) {
    fun getStartDestination(): Route {
        val isLoggedIn = firebaseAuth.isUserAlreadyLoggedIn()
       // return if (isLoggedIn) Route.MainGraph else Route.AuthGraph
        return Route.MainGraph
    }
}
