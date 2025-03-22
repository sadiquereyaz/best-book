package com.nabssam.bestbook.presentation.ui.snackbar

import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch

// 5. UI Integration with Compose
@Composable
fun SnackbarObserver(
    snackbarManager: SnackbarManager,
    snackbarHostState: SnackbarHostState
) {
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        snackbarManager.snackbarFlow.collect { message ->
            scope.launch {
                val result = snackbarHostState.showSnackbar(
                    message = message.message,
                    actionLabel = message.actionLabel,
                    withDismissAction = true,
//                    duration = message.duration
                )

                if (result == SnackbarResult.ActionPerformed) {
                    message.onActionPerformed?.invoke()
                }
            }
        }
    }
}