package com.nabssam.bestbook.domain.repository

import com.nabssam.bestbook.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(email: String, password: String): User
    suspend fun signUp(email: String, password: String, name: String): User
    suspend fun logout()
    fun getCurrentUser(): Flow<User?>
}