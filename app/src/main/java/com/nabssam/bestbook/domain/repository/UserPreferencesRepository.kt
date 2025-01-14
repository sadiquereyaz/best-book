package com.nabssam.bestbook.domain.repository

import com.nabssam.bestbook.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {
    val accessToken: Flow<String?>
    val refreshToken: Flow<String?>
    val user: Flow<User?>
    suspend fun saveTokens(accessToken: String, refreshToken: String)
    suspend fun clearAll()
    suspend fun saveTargetExams(exams: List<String>)
    suspend fun getUserTargetExams(): List<String>
}