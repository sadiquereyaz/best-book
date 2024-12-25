package com.imageclasses.imageclasses.ui.navigation.auth

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.imageclasses.imageclasses.ui.feature.account.auth.SignIn
import com.imageclasses.imageclasses.ui.feature.account.auth.SignUp
import com.imageclasses.imageclasses.ui.navigation.Route
import kotlinx.serialization.Serializable

@Serializable
data object AuthGraphRoute

fun NavGraphBuilder.authGraph(navController: NavController) {
    navigation<Route.AuthGraph>(
        startDestination = Route.SignIn()
    ) {
        composable<Route.SignUp> {
            SignUp(navController = navController)
        }

        composable<Route.SignIn> {
            SignIn(navController)
        }
    }
}