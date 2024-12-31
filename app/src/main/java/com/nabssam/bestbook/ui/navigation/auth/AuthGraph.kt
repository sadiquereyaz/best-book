package com.nabssam.bestbook.ui.navigation.auth

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.nabssam.bestbook.ui.feature.account.auth.SignIn
import com.nabssam.bestbook.ui.feature.account.auth.SignUp
import com.nabssam.bestbook.ui.navigation.Route

fun NavGraphBuilder.authGraph(navController: NavController) {
    navigation<Route.AuthGraph>(
        startDestination =
        Route.SignIn()
    ) {
        composable<Route.SignUp> {
            SignUp(navController = navController)
        }

        composable<Route.SignIn> {
            SignIn(navController)
        }
    }
}