package com.nabssam.bestbook.presentation


import android.content.Intent
import android.os.Build
import android.provider.ContactsContract
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.nabssam.bestbook.data.connectivity.NetworkConnectivityObserver
import com.nabssam.bestbook.data.repository.auth.AuthManager
import com.nabssam.bestbook.domain.model.AppState
import com.nabssam.bestbook.presentation.navigation.AppNavHost
import com.nabssam.bestbook.presentation.navigation.Route
import com.nabssam.bestbook.presentation.ui.components.BBNavSuite
import com.nabssam.bestbook.presentation.ui.components.OfflineDialog

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BestBookApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    authManager: AuthManager,
    connectivityObserver: NetworkConnectivityObserver
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

    //AuthObserver(authManager = authManager, navController = navController)

    val isWeakConnection by connectivityObserver.observe().collectAsState(initial = true)
    OfflineDialog(isVisible = !isWeakConnection, onRetryClick = {
        // Retry logic: check network availability again
        val isStillDisconnected = !connectivityObserver.isNetworkAvailable()
        if (!isStillDisconnected) {
            // Perform any action needed when network is back
        }
    },)

    BBNavSuite(navController = navController, modifier = Modifier)
    { innerPadding ->
        AppNavHost(navController, modifier, innerPadding)
    }
}

@Composable
fun AuthObserver(authManager: AuthManager, navController: NavHostController) {
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(Unit) {
        authManager.authEvents.collect { event ->
            when (event) {
                is AppState.LoggedOut -> {
                    navController.navigate(Route.AuthGraph) {
                        popUpTo(0) // Clear backstack
                    }
                }
                else -> Unit
            }
        }
    }
}
