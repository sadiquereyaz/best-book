package com.nabssam.bestbook.presentation.ui.snackbar

import android.view.ViewTreeObserver
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

@Composable
fun KeyboardAwareSnackbarHost(
    hostState: SnackbarHostState,
    modifier: Modifier = Modifier
) {
    // Get the current view to observe insets
    val view = LocalView.current
    val density = LocalDensity.current

    // Track keyboard visibility and height
    var keyboardHeight by remember { mutableStateOf(0.dp) }
    var isKeyboardVisible by remember { mutableStateOf(false) }

    // Observe keyboard insets
    DisposableEffect(view) {
        val listener = ViewTreeObserver.OnGlobalLayoutListener {
            val insets = ViewCompat.getRootWindowInsets(view)
            if (insets != null) {
                val imeVisible = insets.isVisible(WindowInsetsCompat.Type.ime())
                val imeHeight = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom

                with(density) {
                    keyboardHeight = imeHeight.toDp()
                }
                isKeyboardVisible = imeVisible
            }
        }

        view.viewTreeObserver.addOnGlobalLayoutListener(listener)

        onDispose {
            view.viewTreeObserver.removeOnGlobalLayoutListener(listener)
        }
    }

    // Custom SnackbarHost that positions based on keyboard
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        SnackbarHost(
            hostState = hostState,
            modifier = Modifier
                .padding(bottom = if (isKeyboardVisible) keyboardHeight else 0.dp)
                .padding(0.dp)
        )
    }
}

// Main Scaffold implementation with keyboard-aware snackbar
/*
@Composable
fun AppScaffold(
    appViewModel: AppViewModel,
    content: @Composable (PaddingValues) -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        // Instead of using the default snackbarHost parameter, we're using content padding
        snackbarHost = { },
        content = { paddingValues ->
            Box(modifier = Modifier.fillMaxSize()) {
                // Main content
                content(paddingValues)

                // Custom snackbar host that sits above everything else
                KeyboardAwareSnackbarHost(
                    hostState = snackbarHostState
                )
            }
        }
    )

    // Set up the snackbar observer with our custom host state
    SnackbarObserver(
        snackbarManager = appViewModel.getSnackbarManager(),
        snackbarHostState = snackbarHostState
    )
}*/
