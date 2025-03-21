package com.nabssam.bestbook.domain.usecase.user_detail_use_case

import com.nabssam.bestbook.domain.repository.UserDataStoreRepository
import javax.inject.Inject

class GetUserNameUseCase @Inject constructor(
    private val userDataStoreRepo: UserDataStoreRepository
) {
    suspend operator fun invoke(): String? = userDataStoreRepo.getUserName()
}
