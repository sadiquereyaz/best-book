package com.nabssam.bestbook.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nabssam.bestbook.data.AppPreferences
import com.nabssam.bestbook.data.local.dao.CartDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val appPreferences: AppPreferences,
    private val cartDao: CartDao
) : ViewModel() {

    /*fun getCartItemCount(): Flow<Int> = flow {
        emit(cartDao.getTotalCartCount())
    }*/

    val getCartItemCount: StateFlow<Int> = cartDao.getTotalCartCount()
        .stateIn(viewModelScope, SharingStarted.Lazily, 0)

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
