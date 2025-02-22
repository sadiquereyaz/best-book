package com.nabssam.bestbook.domain.repository

import kotlinx.coroutines.flow.Flow

interface NetworkConnectivityRepository {
    fun observe(): Flow<Boolean>
    fun isNetworkAvailable(): Boolean
}