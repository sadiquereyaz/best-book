package com.nabssam.bestbook.data.remote.api

import com.nabssam.bestbook.data.remote.dto.BannerResponse
import retrofit2.Response
import retrofit2.http.GET

interface BannerApiService {
    @GET("/api/banners/topBanners")
    suspend fun getBanners(/*currentClass: String*/): Response<BannerResponse>
}