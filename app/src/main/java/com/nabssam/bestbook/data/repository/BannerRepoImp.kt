package com.nabssam.bestbook.data.repository

import android.util.Log
import com.nabssam.bestbook.data.mapper.bannerDtoToDomain
import com.nabssam.bestbook.data.remote.api.HomeApiService
import com.nabssam.bestbook.domain.model.Banner
import com.nabssam.bestbook.domain.repository.BannerRepository
import com.nabssam.bestbook.domain.repository.PyqRepository
import com.nabssam.bestbook.domain.usecase.exam_std.GetCurrentClassUseCase
import javax.inject.Inject

private const val TAG = "BANNER_REPO_IMPL"

class BannerRepoImp @Inject constructor(
    private val bannerApi: HomeApiService,
    private val getCurrentClassUseCase: GetCurrentClassUseCase
) : BannerRepository {
    override suspend fun getAll(): Result<List<Banner>> {
        return try {
            val currentClass = getCurrentClassUseCase()
//            Log.d(TAG, "getAll: currentClass: $currentClass")
            val response = bannerApi.getBanners(/*currentClass = currentClass ?: ""*/)
//            Log.d(TAG, "getAll: response: ${response.body()}")
            if (response.isSuccessful) {
                response.body()?.banners?.let {
                    Result.success(
                        it.map { bannerDto ->
                            bannerDto.bannerDtoToDomain()
                        }
                    )
                } ?: Result.failure(Exception("Empty response"))
            } else {
//                Log.e(TAG, "getAll: Error: ${response.code()} - ${response.message()}")
                Result.failure(Exception("Error: ${response.code()} - ${response.message()}"))
            }
        } catch (e: Exception) {
            Log.e(TAG, "getAll: An unexpected error occurred: ${e.message ?: ""}")
            Result.failure(Exception(e.message ?: "An unexpected error occurred"))
        }
    }
}