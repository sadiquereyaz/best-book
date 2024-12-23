package com.imageclasses.imageclasses.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.imageclasses.imageclasses.ui.navigation.auth.authGraph
import com.imageclasses.imageclasses.ui.navigation.main.mainAppGraph
import kotlinx.serialization.Serializable


@Composable
fun AppNavHost(
    navController: NavHostController,
    isLoggedIn: Boolean,
    modifier: Modifier,
    innerPadding: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = if (isLoggedIn) DestinationScreen.mainApp.route else DestinationScreen.ots.route,
        modifier = modifier.padding(innerPadding)
    ) {

        // registration nested graph
        authGraph()

        // main app nested graph
        mainAppGraph(navController)
    }
}
