package com.nabssam.bestbook.presentation.navigation.auth

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.nabssam.bestbook.presentation.navigation.Route
import com.nabssam.bestbook.presentation.ui.account.auth.AuthViewModel
import com.nabssam.bestbook.presentation.ui.account.auth.AuthenticationScreen

@RequiresApi(Build.VERSION_CODES.O)
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

            val viewModel = hiltViewModel<AuthViewModel>()
            AuthenticationScreen(
                onRegistrationComplete = {
                    navController.navigate("home") {
                        popUpTo("registration") { inclusive = true }
                    }
                },
                onLoginSuccess = { navController.navigate(Route.MainGraph) },
                viewModel =viewModel,
            )
        }
        composable<Route.SignUp> {
            //SignUpScreen(navController = navController)
        }
    }
}