package com.imageclasses.imageclasses.ui.navigation.auth

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute

import com.imageclasses.imageclasses.ui.feature.account.auth.SignIn
import com.imageclasses.imageclasses.ui.feature.account.auth.SignInRoute
import com.imageclasses.imageclasses.ui.feature.account.auth.SignUp
import com.imageclasses.imageclasses.ui.feature.account.auth.SignUpRoute
import com.imageclasses.imageclasses.ui.navigation.DestinationScreen
import kotlinx.serialization.Serializable

@Serializable
data object AuthGraphRoute

fun NavGraphBuilder.authGraph(navController: NavController) {
    navigation<AuthGraphRoute>(
        startDestination = SignInRoute()
    ) {
        composable<SignUpRoute> {
            SignUp(navController = navController)
        }

        composable<SignInRoute> {
            SignIn(navController)
        }
    }
}