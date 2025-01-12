package com.nabssam.bestbook.presentation.navigation.auth

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.nabssam.bestbook.presentation.navigation.Route
import com.nabssam.bestbook.presentation.ui.account.auth.SignUpScreen
import com.nabssam.bestbook.presentation.ui.account.auth.claud.AuthenticationScreen

fun NavGraphBuilder.authGraph(navController: NavController) {
    navigation<Route.AuthGraph>(
        startDestination =
        Route.SignIn()
    ) {
        composable<Route.SignIn> {
            /*val viewModel = hiltViewModel<AuthViewModel>()
            val state by viewModel.authState.collectAsState()
            AuthScreen(
                state = state,
                onEvent = viewModel::onEvent,
            ) { navController.navigate(Route.MainGraph) }*/

            AuthenticationScreen(
                onRegistrationComplete = {
                    navController.navigate("home") {
                        popUpTo("registration") { inclusive = true }
                    }
                }
            )
        }
        composable<Route.SignUp> {
            SignUpScreen(navController = navController)
        }
    }
}