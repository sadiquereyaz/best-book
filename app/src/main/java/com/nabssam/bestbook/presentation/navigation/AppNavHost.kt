package com.nabssam.bestbook.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.nabssam.bestbook.presentation.navigation.auth.authGraph
import com.nabssam.bestbook.presentation.navigation.main.mainAppGraph
import com.nabssam.bestbook.presentation.ui.account.auth.AuthState
import com.nabssam.bestbook.presentation.ui.account.auth.VMAuth


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier,
    innerPadding: PaddingValues,
) {

    val viewModel: VMAuth = hiltViewModel()
    val authState by viewModel.state.collectAsStateWithLifecycle()

//    if (!authState.isSignedIn) {
//        LoadingScreen()
//    } else {
        NavHost(
            navController = navController,
            startDestination = getStartDestination(authState),
            modifier = modifier.padding(innerPadding),
        ) {
            // registration nested graph
            authGraph(navController)

            // main app nested graph
            mainAppGraph(navController)
        }
    }
//}

fun getStartDestination(authState: AuthState): Route {
    val isLoggedIn = authState.isSignedIn
    return if (isLoggedIn)
        Route.MainGraph
    else
        Route.MainGraph
//        Route.AuthGraph
}

@Composable
private fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LinearProgressIndicator()
    }
}