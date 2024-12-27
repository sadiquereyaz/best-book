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
        startDestination = DestinationScreen.auth_signup.route
    ) {

        composable (route=DestinationScreen.auth_signup.route) {
            SignUp(navController = navController)
        }
        composable(route=DestinationScreen.auth_signin.route) {
        SignIn(navController)
        }
    }
}