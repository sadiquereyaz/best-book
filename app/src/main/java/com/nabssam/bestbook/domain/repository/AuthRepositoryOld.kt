package com.nabssam.bestbook.domain.repository

import com.nabssam.bestbook.domain.model.UserOld
import kotlinx.coroutines.flow.Flow

interface AuthRepositoryOld {
    suspend fun login(email: String, password: String): UserOld
    suspend fun signUp(email: String, password: String, name: String): UserOld
    suspend fun logout()
    fun getCurrentUser(): Flow<UserOld?>
}