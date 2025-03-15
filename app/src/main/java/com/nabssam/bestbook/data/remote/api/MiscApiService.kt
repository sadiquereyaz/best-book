package com.nabssam.bestbook.data.remote.api

import com.nabssam.bestbook.data.remote.dto.BannerResponse
import com.nabssam.bestbook.data.remote.dto.PyqResponse
import retrofit2.Response
import retrofit2.http.GET

interface HomeApiService {
    @GET("/api/banners/topBanners")
    suspend fun getBanners(/*currentClass: String*/): Response<BannerResponse>

    @GET("/api/pyq")
    suspend fun getPyq(): Response<PyqResponse>
}