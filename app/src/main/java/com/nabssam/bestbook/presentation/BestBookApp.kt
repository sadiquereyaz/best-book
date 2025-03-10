package com.nabssam.bestbook.presentation


import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
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

@Composable
fun BestBookApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    authManager: AuthManager,
    connectivityObserver: NetworkConnectivityObserver
) {
    val context = LocalContext.current
    //listen to authenticated events
    LaunchedEffect(Unit) {
        authManager.authEvents.collect { events ->
            when (events) {
                is AppState.DeviceConflict -> {
                    Log.d("BestBookApp", "Device Conflict")
                    showToast(context, "device conflict")
                }
                is AppState.LoggedOut -> {
                    Log.d("BestBookApp", "LoggedOut State")
                    navController.navigate(Route.AuthGraph) {
                        popUpTo(0) // Clear backstack
                    }
                }

                else -> Unit
            }
        }
    }

//    AuthObserver(authManager = authManager, navController = navController)

    val appViewModel: AppViewModel = hiltViewModel()
    val isSignedIn: Boolean by appViewModel.authState.collectAsState()
    val cartItemCount: Int by appViewModel.getCartItemCount.collectAsState()
    Log.d("BestBookApp", "cartItemCount: $cartItemCount")

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
            authManager.handleDeviceConflict()
        },
    )
}