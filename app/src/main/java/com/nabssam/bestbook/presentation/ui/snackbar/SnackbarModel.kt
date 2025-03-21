package com.nabssam.bestbook.presentation.ui.snackbar

import androidx.compose.material3.SnackbarDuration

// 1. SnackbarManager interface and data classes
sealed class SnackbarType {
    data object SUCCESS : SnackbarType()
    data object ERROR : SnackbarType()
    data object INFO : SnackbarType()
    data object WARNING : SnackbarType()
}

data class SnackbarMessage(
    val message: String,
    val type: SnackbarType = SnackbarType.INFO,
    val duration: SnackbarDuration = SnackbarDuration.Long,
    val actionLabel: String? = null,
    val onActionPerformed: (() -> Unit)? = null
)
