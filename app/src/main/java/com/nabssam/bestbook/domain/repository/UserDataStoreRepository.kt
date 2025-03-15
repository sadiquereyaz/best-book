package com.nabssam.bestbook.domain.repository

import com.nabssam.bestbook.domain.model.User
import kotlinx.coroutines.flow.Flow

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

interface UserDataStoreRepository {
    val accessToken: Flow<String?>
    val refreshToken: Flow<String?>
    val user: Flow<User?>
    suspend fun saveTokens(accessToken: String, refreshToken: String)
    suspend fun clearAll()
    //    suspend fun saveTargetExams(exams: List<String>)
    suspend fun getUserTargetExams(): List<String>
    suspend fun getCurrentClass(): String?
}
