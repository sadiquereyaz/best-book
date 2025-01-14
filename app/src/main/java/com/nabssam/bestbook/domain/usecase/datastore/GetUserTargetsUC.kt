package com.nabssam.bestbook.domain.usecase.datastore

import com.nabssam.bestbook.data.repository.UserPrefRepoImpl
import javax.inject.Inject

class GetUserTargetsUC @Inject constructor(
    private val userPrefRepoImpl: UserPrefRepoImpl
) {
    suspend operator fun invoke(): List<String> = userPrefRepoImpl.getUserTargetExams()
}