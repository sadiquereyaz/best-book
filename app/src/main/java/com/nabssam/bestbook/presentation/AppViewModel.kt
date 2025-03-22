package com.nabssam.bestbook.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nabssam.bestbook.data.AppPreferences
import com.nabssam.bestbook.data.local.dao.CartDao
import com.nabssam.bestbook.data.repository.auth.AuthManager
import com.nabssam.bestbook.data.repository.auth.TokenStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val appPreferences: AppPreferences,
    private val cartDao: CartDao,
    private val tokenStorage: TokenStorage,
    private val authManager: AuthManager
) : ViewModel() {

    /*fun getCartItemCount(): Flow<Int> = flow {
        emit(cartDao.getTotalCartCount())
    }*/

    val getCartItemCount: StateFlow<Int?> = cartDao.getTotalCartCount()
        .stateIn(viewModelScope, SharingStarted.Lazily, 0)

    // Observing cart count as StateFlow
    val cartItemCount: StateFlow<Int> = appPreferences.cartItemCount
        .stateIn(viewModelScope, SharingStarted.Lazily, 0)

    // Observing authentication state as StateFlow
    val authState: StateFlow<Boolean> = appPreferences.authState
        .stateIn(viewModelScope, SharingStarted.Lazily, false)

    fun isLoggedIn() {
        viewModelScope.launch {
           updateAuthState(tokenStorage.getAccessToken() != null)
        }
    }

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

data class AppUiState(
    val cartItemCount: Int = 0,
    val isLoggedIn: Boolean = false
)