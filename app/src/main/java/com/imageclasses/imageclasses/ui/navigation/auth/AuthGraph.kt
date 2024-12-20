package com.imageclasses.imageclasses.ui.navigation.auth

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.imageclasses.imageclasses.ui.feature.account.auth.AuthScreen
import com.imageclasses.imageclasses.ui.navigation.DestinationScreen


fun NavGraphBuilder.authGraph() {
    navigation(
        route = DestinationScreen.ots.route,
        startDestination = DestinationScreen.intro.route
    ) {
        composable(route = DestinationScreen.intro.route) {
            //IntroScreen()
        }
        composable(route = DestinationScreen.auth.route) {
            AuthScreen()
        }
    }
}