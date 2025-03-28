package com.nabssam.bestbook.presentation.navigation.auth

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.nabssam.bestbook.presentation.navigation.Route
import com.nabssam.bestbook.presentation.ui.account.auth.AuthenticationScreen
import com.nabssam.bestbook.presentation.ui.account.auth.VMAuth

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

            val viewModel = hiltViewModel<VMAuth>()
            AuthenticationScreen(
                onLoginSuccess = {
                    navController.navigate(Route.MainGraph) {
                        popUpTo(Route.AuthGraph) {
                            inclusive = true
                        }
                    }
                },
                viewModel = viewModel,
            )
        }
        composable<Route.SignUp> {
            //SignUpScreen(navController = navController)
        }
    }
}