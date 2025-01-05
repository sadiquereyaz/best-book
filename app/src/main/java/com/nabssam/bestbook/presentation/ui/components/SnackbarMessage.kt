package com.nabssam.bestbook.presentation.ui.components

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import com.nabssam.bestbook.domain.repository.NetworkConnectivityRepository
import kotlinx.coroutines.flow.collectLatest

data class SnackbarMessage(
    val message: String,
    val duration: SnackbarDuration = SnackbarDuration.Short,
    val actionLabel: String? = null,
    val onActionPerformed: (() -> Unit)? = null
)

// SnackBarManager.kt
@Composable
fun rememberSnackbarState(): SnackbarHostState {
    return remember { SnackbarHostState() }
}

// NetworkSnackbar.kt
@Composable
fun NetworkSnackbar(
    networkConnectivityObserver: NetworkConnectivityRepository,
    snackbarHostState: SnackbarHostState
) {
    LaunchedEffect(Unit) {
        networkConnectivityObserver.observe().collectLatest { isConnected ->
            if (!isConnected) {
                snackbarHostState.showSnackbar(
                    message = "No internet connection",
                    duration = SnackbarDuration.Indefinite,
                    actionLabel = "Dismiss"
                )
            }
        }

    }
}