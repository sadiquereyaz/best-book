package com.nabssam.bestbook.utils

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import com.nabssam.bestbook.data.repository.auth.AuthManager
import com.nabssam.bestbook.domain.model.AppState
import com.nabssam.bestbook.presentation.navigation.Route
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
    fun AuthObserver(authManager: AuthManager, navController: NavController) {
        val context = LocalContext.current
        val lifecycleOwner = LocalLifecycleOwner.current

        // Add more detailed logging
        LaunchedEffect(key1 = true) {
            /*Log.d("AUTH_FLOW", "AuthObserver: Starting collection")
            try {
                authManager.authEvents.collect { event ->
                    Log.d("AUTH_FLOW", "AuthObserver: Received event: $event")
                    when (event) {
                        is AppState.DeviceConflict -> {
                            Log.d("AUTH_FLOW", "AuthObserver: Device Conflict")
                        }
                        is AppState.LoggedOut -> {
                            Log.d("AUTH_FLOW", "AuthObserver: LoggedOut State, navigating...")
                            // Try dispatching this navigation on the main thread for safety
                            withContext(Dispatchers.Main) {
                                navController.navigate(Route.AuthGraph) {
                                    popUpTo(0) // Clear backstack
                                }
                            }
                            Log.d("AUTH_FLOW", "AuthObserver: Navigation completed")
                        }
                        else -> {
                            Log.d("AUTH_FLOW", "AuthObserver: Unhandled event: $event")
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("AUTH_FLOW", "AuthObserver: Error in collection", e)
            }*/
        }
    }