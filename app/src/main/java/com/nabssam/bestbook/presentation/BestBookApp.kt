package com.nabssam.bestbook.presentation


import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.nabssam.bestbook.data.connectivity.NetworkConnectivityObserver
import com.nabssam.bestbook.data.repository.auth.AuthManager
import com.nabssam.bestbook.domain.model.AppState
import com.nabssam.bestbook.presentation.navigation.AppNavHost
import com.nabssam.bestbook.presentation.navigation.Route
import com.nabssam.bestbook.presentation.ui.components.BBNavSuite
import com.nabssam.bestbook.presentation.ui.components.OfflineDialog
import com.nabssam.bestbook.utils.AuthObserver
import com.nabssam.bestbook.utils.showToast
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun BestBookApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    authManager: AuthManager,
    connectivityObserver: NetworkConnectivityObserver,
    appViewModel: AppViewModel = hiltViewModel(),
) {
    val isSignedIn: Boolean by appViewModel.authState.collectAsState()
    val cartItemCount: Int by appViewModel.getCartItemCount.collectAsState()

    LaunchedEffect(Unit) {
        authManager.appState.collect { appState ->
            when (appState) {
                is AppState.LoggedOut -> {
                    Log.d("BestBookApp", "LOGOUT DONE")
                    navController.navigate(Route.AuthGraph) {
                        popUpTo(Route.MainGraph) { inclusive = true }
                    }
                }
                else -> Unit
            }
        }
    }

    BBNavSuite(
        navController = navController,
        modifier = Modifier,
        authManager = authManager,
        cartItemCount = cartItemCount
    ) { innerPadding ->

        AppNavHost(navController, modifier, innerPadding, isSignedIn)
    }

    val isWeakConnection by connectivityObserver.observe().collectAsState(initial = false)
    OfflineDialog(
        isVisible = !isWeakConnection,
        onRetryClick = {
            // Retry logic: check network availability again
            val isStillDisconnected = !connectivityObserver.isNetworkAvailable()
            if (!isStillDisconnected) {
                // Perform any action needed when network is back
            }
        },
    )
}