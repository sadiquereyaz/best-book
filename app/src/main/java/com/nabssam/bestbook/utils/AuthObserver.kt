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

@Composable
fun AuthObserver(authManager: AuthManager, navController: NavController) {
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(Unit) {
        authManager.authEvents.collect { event ->
            when (event) {
                is AppState.DeviceConflict -> {
                    Log.d("BestBookApp", "Device Conflict")
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
}
