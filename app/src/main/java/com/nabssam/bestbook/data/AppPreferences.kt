package com.nabssam.bestbook.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

// Extension property for DataStore
private val Context.dataStore by preferencesDataStore("app_preferences")

@Singleton
class AppPreferences @Inject constructor(@ApplicationContext private val context: Context) {

    private val dataStore = context.dataStore

    companion object {
        val CART_ITEM_COUNT = intPreferencesKey("cart_item_count")
        val AUTH_STATE = booleanPreferencesKey("auth_state") // true = logged in, false = logged out
    }

    // Observe Cart Count
    val cartItemCount: Flow<Int> = dataStore.data
        .map { it[CART_ITEM_COUNT] ?: 0 } // Default to 0

    // Observe Auth State
    val authState: Flow<Boolean> = dataStore.data
        .map { it[AUTH_STATE] ?: false } // Default to logged out

    // Update Cart Count
    suspend fun updateCartItemCount(count: Int) {
        dataStore.edit { it[CART_ITEM_COUNT] = count }
    }

    // Update Auth State
    suspend fun updateAuthState(isLoggedIn: Boolean) {
        dataStore.edit { it[AUTH_STATE] = isLoggedIn }
    }
}
