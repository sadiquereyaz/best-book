package com.nabssam.bestbook.domain.repository

import com.nabssam.bestbook.domain.model.Banner
import com.nabssam.bestbook.utils.Resource
import kotlinx.coroutines.flow.Flow

interface BannerRepository {
    suspend fun getAll(): Result<List<Banner>>
}