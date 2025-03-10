package com.nabssam.bestbook.presentation.ui.snackbar

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

interface SnackbarManager {
    val snackbarFlow: Flow<SnackbarMessage>
    suspend fun showSnackbar(message: SnackbarMessage)
}

// 2. SnackbarManager implementation
class SnackbarManagerImpl @Inject constructor() : SnackbarManager {
    // Use a Channel to handle messages - will process one at a time
    private val _snackbarChannel = Channel<SnackbarMessage>(Channel.BUFFERED)

    // Convert channel to flow for collection
    override val snackbarFlow = _snackbarChannel.receiveAsFlow()

    override suspend fun showSnackbar(message: SnackbarMessage) {
        _snackbarChannel.send(message)
    }
}