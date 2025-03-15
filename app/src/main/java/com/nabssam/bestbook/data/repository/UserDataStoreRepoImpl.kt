package com.nabssam.bestbook.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.nabssam.bestbook.domain.model.User
import com.nabssam.bestbook.domain.repository.UserDataStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Writes to DataStore should always be suspend since they perform disk I/O.
 *
 * Use Flow when you want to continuously observe changes in DataStore, such as when using collect() in a ViewModel.
 * Flow is reactive, meaning it emits updates whenever DataStore changes.
 * Useful for Live UI updates in Jetpack Compose or observing session tokens.
 *
 * Use suspend for One-time Reads
 * ✔️ Use suspend functions when you just need the value once (e.g., fetching a setting at app launch).
 *
 * suspend fun getAccessToken(): String? {
 *     return dataStore.data.firstOrNull()?.get(ACCESS_TOKEN_KEY)
 * }
 * Why?
 * firstOrNull() gets the value only once and does not keep listening for changes.
 * Good for functions like authentication checks.
 **/

private object PreferencesKeys {
    val CURRENT_CLASS = stringPreferencesKey("current_class")
    val TARGET_EXAMS = stringPreferencesKey("target_exams")
    val ACCESS_TOKEN = stringPreferencesKey("access_token")
    val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
    val USER_ID = stringPreferencesKey("user_id")
    val USERNAME = stringPreferencesKey("username")
    val USER_EMAIL = stringPreferencesKey("user_email")
    val USER_PHONE = stringPreferencesKey("user_phone")
    val USER_ROLE = booleanPreferencesKey("user_role")
    val USER_AVATAR = stringPreferencesKey("user_avatar")
    val EBOOK = stringPreferencesKey("ebook")
    val SCHOOL = stringPreferencesKey("school")
}

class UserDataStoreRepoImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : UserDataStoreRepository {

    override val accessToken: Flow<String?> = dataStore.data.map { preferences ->
        preferences[PreferencesKeys.ACCESS_TOKEN]
    }

    override val refreshToken: Flow<String?> = dataStore.data.map { preferences ->
        preferences[PreferencesKeys.REFRESH_TOKEN]
    }

    override val user: Flow<User?> = dataStore.data.map { preferences ->
        val userId = preferences[PreferencesKeys.USER_ID] ?: return@map null
        val username = preferences[PreferencesKeys.USERNAME] ?: return@map null
        val email = preferences[PreferencesKeys.USER_EMAIL] ?: return@map null
        val role = preferences[PreferencesKeys.USER_ROLE] ?: return@map null
        val avatarUrl = preferences[PreferencesKeys.USER_AVATAR] ?: return@map null
        val phone = preferences[PreferencesKeys.USER_PHONE] ?: return@map null
        val targetExams = preferences[PreferencesKeys.TARGET_EXAMS]?.split(",") ?: return@map null
        val currentClass = preferences[PreferencesKeys.CURRENT_CLASS] ?: return@map null
        val school = preferences[PreferencesKeys.SCHOOL] ?: return@map null
        val subscribedEbooks = preferences[PreferencesKeys.EBOOK]?.split(",") ?: return@map null


        User(
            id = userId,
            picUrl = avatarUrl,
            username = username,
            email = email,
            isAdmin = role,
            phone = phone,
            targetExams = targetExams,
            currentClass = currentClass,
            schoolName = school,
            subscribedEbooks = subscribedEbooks,
        )
    }

    override suspend fun getCurrentClass(): String? = dataStore.data.firstOrNull()?.get(PreferencesKeys.CURRENT_CLASS)


    /*val targetExam: Flow<String> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[PreferencesKeys.TARGET_EXAMS]
                ?: DEFAULT_CATEGORY_id   //TODO: change to "All_books"
        }*/

/*    override suspend fun saveTargetExams(exams: List<String>) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.TARGET_EXAMS] = exams.joinToString(",")
        }
    }*/

    override suspend fun getUserTargetExams(): List<String> {
        val mutablePreferences = dataStore.data.first()
        val targetExams = mutablePreferences[PreferencesKeys.TARGET_EXAMS] ?: return emptyList()

        //Log.d("DATA_S_LIST", "fetched all exams: ${targetExams.split(",").map { it.trim() }}")

        return targetExams.split(",").map { it.trim() }
    }

    suspend fun saveUser(user: User) {
        //Log.d("DATASTORE", "Saving user target exams: ${user.targetExams}")

        dataStore.edit { preferences ->
            preferences[PreferencesKeys.USER_ID] = user.id
            preferences[PreferencesKeys.USERNAME] = user.username
            preferences[PreferencesKeys.USER_EMAIL] = user.email
            preferences[PreferencesKeys.USER_ROLE] = user.isAdmin
            preferences[PreferencesKeys.USER_AVATAR] = user.picUrl
            preferences[PreferencesKeys.USER_PHONE] = user.phone
            preferences[PreferencesKeys.ACCESS_TOKEN] = user.accessToken  // todo uncomment these
            preferences[PreferencesKeys.REFRESH_TOKEN] = user.refreshToken
            preferences[PreferencesKeys.CURRENT_CLASS] = user.currentClass
            preferences[PreferencesKeys.SCHOOL] = user.schoolName
            preferences[PreferencesKeys.EBOOK] = user.subscribedEbooks.joinToString(",")
            preferences[PreferencesKeys.TARGET_EXAMS] = user.targetExams.joinToString(",")
        }

        //Log.d("DATASTORE", "Saved user target exams for datastore: ${user.targetExams.joinToString(",")}")
    }

    override suspend fun saveTokens(accessToken: String, refreshToken: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.ACCESS_TOKEN] = accessToken
            preferences[PreferencesKeys.REFRESH_TOKEN] = refreshToken
        }
    }

    override suspend fun clearAll() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}


