package com.nabssam.bestbook.domain.repository

import com.nabssam.bestbook.domain.model.Banner
import com.nabssam.bestbook.domain.model.Pyq
import com.nabssam.bestbook.utils.Resource
import kotlinx.coroutines.flow.Flow

interface PyqRepository {
    suspend fun getAll(): Result<List<Pyq>>
}
