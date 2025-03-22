package com.nabssam.bestbook.domain.usecase.user_detail_use_case

import com.nabssam.bestbook.data.repository.UserDataStoreRepoImpl
import javax.inject.Inject

class GetUserTargetsUC @Inject constructor(
    private val userDataStoreRepoImpl: UserDataStoreRepoImpl
) {
    suspend operator fun invoke(): List<String> {
        val targetExams = userDataStoreRepoImpl.getUserTargetExams()
//        Log.d("GetUserTargetsUC", "Fetched target exams: $targetExams")
        return targetExams
    }

}