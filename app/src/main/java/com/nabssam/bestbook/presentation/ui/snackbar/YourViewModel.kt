package com.nabssam.bestbook.presentation.ui.snackbar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nabssam.bestbook.data.connectivity.NetworkConnectivityObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class YourViewModel @Inject constructor(
    private val networkConnectivityObserver: NetworkConnectivityObserver
) : ViewModel() {
    
    // Collect network status if needed in ViewModel
    private val _isConnected = MutableStateFlow(true)
    val isConnected: StateFlow<Boolean> = _isConnected.asStateFlow()
    
    init {
        viewModelScope.launch {
            networkConnectivityObserver.observe()
                .collect { isConnected ->
                    _isConnected.value = isConnected
                }
        }
    }
}