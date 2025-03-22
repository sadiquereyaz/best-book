package com.nabssam.bestbook.domain.repository

import com.nabssam.bestbook.domain.model.Banner

interface BannerRepository {
    suspend fun getAll(): Result<List<Banner>>
}
