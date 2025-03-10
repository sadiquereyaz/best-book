package com.nabssam.bestbook.presentation.navigation

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.nabssam.bestbook.data.repository.auth.AuthManager
import com.nabssam.bestbook.domain.model.AppState
import com.nabssam.bestbook.presentation.navigation.auth.authGraph
import com.nabssam.bestbook.presentation.navigation.main.mainAppGraph
import com.nabssam.bestbook.presentation.ui.account.auth.AuthState
import com.nabssam.bestbook.presentation.ui.account.auth.VMAuth
import com.nabssam.bestbook.utils.AuthObserver
import kotlinx.coroutines.launch


@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier,
    innerPadding: PaddingValues,
    isSign: Boolean,
    viewModel: VMAuth = hiltViewModel(),
) {
    val authState by viewModel.state.collectAsStateWithLifecycle()

    if (/*authState.isLoading*/false) {
        LoadingScreen()
    } else {
        NavHost(
            navController = navController,
            startDestination = getStartDestination(isSign, authState),
            modifier = modifier.padding(innerPadding),
        ) {
            // registration nested graph
            authGraph(navController)

            // main app nested graph
            mainAppGraph(navController)
        }
    }
}

fun getStartDestination(isSign: Boolean, authState: AuthState): Route {
    val isLoggedIn = authState.isSignedIn
    return if (isSign) Route.MainGraph
//    return if (isLoggedIn) Route.MainGraph
    else
//        Route.MainGraph
        Route.AuthGraph
}

@Composable
 fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LinearProgressIndicator()
    }
}