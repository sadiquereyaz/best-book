package com.nabssam.bestbook.domain.usecase.exam_std

import com.nabssam.bestbook.domain.repository.UserDataStoreRepository
import javax.inject.Inject

class GetCurrentClassUseCase @Inject constructor(
    private val userDataStoreRepo: UserDataStoreRepository
) {
    suspend operator fun invoke(): String? = userDataStoreRepo.getCurrentClass()
}