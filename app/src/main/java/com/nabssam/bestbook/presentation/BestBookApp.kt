package com.nabssam.bestbook.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.nabssam.bestbook.data.repository.auth.AuthManager
import com.nabssam.bestbook.domain.model.AppState
import com.nabssam.bestbook.presentation.navigation.AppNavHost
import com.nabssam.bestbook.presentation.navigation.Route
import com.nabssam.bestbook.presentation.ui.components.BBNavSuite

@Composable
fun BestBookApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    authManager: AuthManager
) {
    //listen to authenticated events
    LaunchedEffect(Unit) {
        authManager.authEvents.collect {events->
            when(events){
                AppState.LoggedOut -> {
                    navController.navigate(Route.AuthGraph) {
                        popUpTo(0) // Clear backstack
                    }
                }
                else -> Unit
            }

        }
    }

    BBNavSuite(navController = navController, modifier = Modifier)
    { innerPadding ->
        AppNavHost(navController, modifier, innerPadding)
    }
}
