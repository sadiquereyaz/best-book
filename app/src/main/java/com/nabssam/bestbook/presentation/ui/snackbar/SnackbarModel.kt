package com.nabssam.bestbook.presentation.ui.snackbar

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

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

/*

// 4. ViewModel integration
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
    
    private fun retry() {
        // Implement retry logic here
    }
}*/
