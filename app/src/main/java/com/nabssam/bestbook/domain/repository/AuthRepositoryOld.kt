package com.nabssam.bestbook.domain.repository

import com.nabssam.bestbook.domain.model.Unit1
import kotlinx.coroutines.flow.Flow

interface AuthRepositoryOld {
    suspend fun login(email: String, password: String): Unit1
    suspend fun signUp(email: String, password: String, name: String): Unit1
    suspend fun logout()
    fun getCurrentUser(): Flow<Unit1?>
}