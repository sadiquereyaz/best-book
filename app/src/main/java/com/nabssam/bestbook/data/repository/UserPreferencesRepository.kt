package com.nabssam.bestbook.data.repository

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.nabssam.bestbook.data.remote.dto.auth.Avatar
import com.nabssam.bestbook.domain.model.User
import com.nabssam.bestbook.utils.Constants.DEFAULT_CATEGORY
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private object PreferencesKeys {
    val TARGET_EXAM = stringPreferencesKey("favorite_category")
    val ACCESS_TOKEN = stringPreferencesKey("access_token")
    val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
    val USER_ID = stringPreferencesKey("user_id")
    val USERNAME = stringPreferencesKey("username")
    val USER_EMAIL = stringPreferencesKey("user_email")
    val USER_ROLE = stringPreferencesKey("user_role")
    val USER_AVATAR = stringPreferencesKey("user_avatar")
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
            preferences[PreferencesKeys.TARGET_EXAM]
                ?: DEFAULT_CATEGORY   //TODO: change to "All_books"
        }

    suspend fun saveTargetExam(category: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.TARGET_EXAM] = category
        }
    }

    suspend fun saveAuthTokens(accessToken: String, refreshToken: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.ACCESS_TOKEN] = accessToken
            preferences[PreferencesKeys.REFRESH_TOKEN] = refreshToken
        }
    }

    suspend fun saveUser(user: User) {
        Log.d("USER_ID_ds", user._id)

        dataStore.edit { preferences ->
            preferences[PreferencesKeys.USER_ID] = user._id
            preferences[PreferencesKeys.USERNAME] = user.username
            preferences[PreferencesKeys.USER_EMAIL] = user.email
            preferences[PreferencesKeys.USER_ROLE] = user.role
            preferences[PreferencesKeys.USER_AVATAR] = user.avatar.url
        }
    }

    val accessToken: Flow<String?> = dataStore.data.map { preferences ->
        preferences[PreferencesKeys.ACCESS_TOKEN]
    }

    val refreshToken: Flow<String?> = dataStore.data.map { preferences ->
        preferences[PreferencesKeys.REFRESH_TOKEN]
    }

    val user: Flow<User?> = dataStore.data.map { preferences ->
        val userId = preferences[PreferencesKeys.USER_ID] ?: return@map null
        val username = preferences[PreferencesKeys.USERNAME] ?: return@map null
        val email = preferences[PreferencesKeys.USER_EMAIL] ?: return@map null
        val role = preferences[PreferencesKeys.USER_ROLE] ?: return@map null
        val avatarUrl = preferences[PreferencesKeys.USER_AVATAR] ?: ""

        User(
            _id = userId,
            avatar = Avatar(
                url = avatarUrl,
                localPath = "",
                _id = "" // We don't need to store avatar ID locally
            ),
            username = username,
            email = email,
            role = role,
            loginType = "EMAIL_PASSWORD", // Default value
            isEmailVerified = false, // Default value
            createdAt = "", // We don't need these timestamps locally
            updatedAt = ""
        )
    }

    suspend fun clearAll() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}

