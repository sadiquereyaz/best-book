package com.nabssam.bestbook.domain.usecase.exam_std

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