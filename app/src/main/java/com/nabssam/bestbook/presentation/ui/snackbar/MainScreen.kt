/*
package com.nabssam.bestbook.presentation.ui.snackbar

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nabssam.bestbook.presentation.ui.components.SnackbarMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

// 1. Basic usage in a screen
@Composable
fun MainScreen() {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { 
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { paddingValues ->
        Button(
            modifier = Modifier.padding(paddingValues),
            onClick = {
                scope.launch {
                    snackbarHostState.showSnackbar(
                        message = "Hello Snackbar!",
                        actionLabel = "Dismiss"
                    )
                }
            }
        ) {
            Text("Show Snackbar")
        }
    }
}

// 2. Advanced usage with custom controller
@Composable
fun FeatureScreen(
    viewModel: FeatureViewModel = hiltViewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val snackbarController = rememberSnackbarController(snackbarHostState)

    Scaffold(
        snackbarHost = { 
            AppSnackbar(snackbarHostState = snackbarHostState)
        }
    ) { paddingValues ->
        // Screen content
        
        // Show Snackbar on error
        LaunchedEffect(viewModel.error) {
            viewModel.error?.let { error ->
                snackbarController.showSnackbar(
                    message = error,
                    duration = SnackbarDuration.Long,
                    actionLabel = "Retry",
                    onActionPerformed = {
                        viewModel.retry()
                    }
                )
            }
        }
    }
}

// 3. Using SnackbarManager in ViewModel
@HiltViewModel
class FeatureViewModel @Inject constructor(
    private val snackbarManager: SnackbarManager
) : ViewModel() {
    
    fun handleEvent(event: UiEvent) {
        viewModelScope.launch {
            when (event) {
                is UiEvent.Success -> {
                    snackbarManager.showSnackbar(
                        SnackbarMessage(
                            message = "Operation successful",
                            type = SnackbarType.SUCCESS,
                            duration = SnackbarDuration.Short
                        )
                    )
                }
                is UiEvent.Error -> {
                    snackbarManager.showSnackbar(
                        SnackbarMessage(
                            message = event.message,
                            type = SnackbarType.ERROR,
                            duration = SnackbarDuration.Long,
                            actionLabel = "Retry",
                            onActionPerformed = { retry() }
                        )
                    )
                }
            }
        }
    }
}

// 4. Global Snackbar observer
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
                    duration = message.duration
                )
                
                if (result == SnackbarResult.ActionPerformed) {
                    message.onActionPerformed?.invoke()
                }
            }
        }
    }
}*/
