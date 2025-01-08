package com.nabssam.bestbook.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private object PreferencesKeys {
    val TARGER_EXAM = stringPreferencesKey("favorite_category")
}

class UserPreferencesRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    val targetExam: Flow<String> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[PreferencesKeys.TARGER_EXAM] ?: "appliances"   //TODO: change to "All_books"
        }

    suspend fun saveTargetExam(category: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.TARGER_EXAM] = category
        }
    }
}