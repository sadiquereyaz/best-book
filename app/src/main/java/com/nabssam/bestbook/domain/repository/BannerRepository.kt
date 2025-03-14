package com.nabssam.bestbook.domain.repository

import com.nabssam.bestbook.domain.model.Banner
import com.nabssam.bestbook.utils.Resource
import kotlinx.coroutines.flow.Flow

interface BannerRepository {
    suspend fun add(book: Banner): Flow<Resource<String>>
    suspend fun getAll1(targetExam:String): Flow<Resource<List</*Banner*/String>>>
    suspend fun getAll(): Result<List<Banner>>
}

/*
interface BannerRepository {
    suspend fun getAll(): Resource<List<Banner>>
}*/