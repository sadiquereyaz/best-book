package com.nabssam.bestbook.presentation.ui.snackbar

// 1. SnackbarManager interface and data classes
sealed class SnackbarType {
    object SUCCESS : SnackbarType()
    object ERROR : SnackbarType()
    object INFO : SnackbarType()
    object WARNING : SnackbarType()
}

enum class SnackbarDuration {
    Short, // Maps to SnackbarDuration.Short
    Long   // Maps to SnackbarDuration.Long
}

data class SnackbarMessage(
    val message: String,
    val type: SnackbarType = SnackbarType.INFO,
    val duration: SnackbarDuration = SnackbarDuration.Short,
    val actionLabel: String? = null,
    val onActionPerformed: (() -> Unit)? = null
)
