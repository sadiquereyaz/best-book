package com.nabssam.bestbook.domain.repository

import com.nabssam.bestbook.domain.model.Unit
import kotlinx.coroutines.flow.Flow

interface AuthRepositoryOld {
    suspend fun login(email: String, password: String): Unit
    suspend fun signUp(email: String, password: String, name: String): Unit
    suspend fun logout()
    fun getCurrentUser(): Flow<Unit?>
}