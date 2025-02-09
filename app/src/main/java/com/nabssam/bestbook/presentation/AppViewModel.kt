package com.nabssam.bestbook.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nabssam.bestbook.data.AppPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val appPreferences: AppPreferences
) : ViewModel() {

    // Observing cart count as StateFlow
    val cartItemCount: StateFlow<Int> = appPreferences.cartItemCount
        .stateIn(viewModelScope, SharingStarted.Lazily, 0)

    // Observing authentication state as StateFlow
    val authState: StateFlow<Boolean> = appPreferences.authState
        .stateIn(viewModelScope, SharingStarted.Lazily, false)

    // Update cart item count
    fun updateCartCount(count: Int) {
        viewModelScope.launch {
            appPreferences.updateCartItemCount(count)
        }
    }

    // Update authentication state
    fun updateAuthState(isLoggedIn: Boolean) {
        viewModelScope.launch {
            appPreferences.updateAuthState(isLoggedIn)
        }
    }
}
