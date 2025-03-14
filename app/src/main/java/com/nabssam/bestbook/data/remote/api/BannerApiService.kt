package com.nabssam.bestbook.data.remote.api

import retrofit2.Response
import retrofit2.http.GET

interface BannerApiService {
    @GET("/api/banners")
    suspend fun getBanners(): Response<BannerResponse>

}

data class BannerResponse(
    val data: List<BannerDto>,
    val message: String,
    val success: Boolean
)

data class BannerDto(
    val imageUrl: String,
    val link: String
)

