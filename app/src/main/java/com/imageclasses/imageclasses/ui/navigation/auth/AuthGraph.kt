package com.imageclasses.imageclasses.ui.navigation.auth

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation

import com.imageclasses.imageclasses.ui.feature.account.auth.SignIn
import com.imageclasses.imageclasses.ui.feature.account.auth.SignInRoute
import com.imageclasses.imageclasses.ui.feature.account.auth.SignUp
import com.imageclasses.imageclasses.ui.feature.account.auth.SignUpRoute
import com.imageclasses.imageclasses.ui.navigation.DestinationScreen


fun NavGraphBuilder.authGraph(navController: NavController) {
    navigation(
        route = DestinationScreen.ots.route,
        startDestination = SignUpRoute().route
    ) {

        composable (route=SignUpRoute().route) {
            SignUp(navController = navController)
        }
        composable(route=SignInRoute().route) {
        SignIn(navController)
        }
    }
}