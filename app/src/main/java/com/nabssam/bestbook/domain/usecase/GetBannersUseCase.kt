package com.nabssam.bestbook.domain.usecase

import com.nabssam.bestbook.domain.model.Banner
import com.nabssam.bestbook.domain.repository.BannerRepository
import com.nabssam.bestbook.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBannersUseCase @Inject constructor(
    private val repository: BannerRepository
) {
    suspend operator fun invoke(targetExam: String): Flow<Resource<List</*Banner*/String>>> {
        return repository.getAll(targetExam)
    }
}