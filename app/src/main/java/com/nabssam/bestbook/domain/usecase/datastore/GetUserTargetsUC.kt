package com.nabssam.bestbook.domain.usecase.datastore

import android.util.Log
import com.nabssam.bestbook.data.repository.UserPrefRepoImpl
import javax.inject.Inject

class GetUserTargetsUC @Inject constructor(
    private val userPrefRepoImpl: UserPrefRepoImpl
) {
    suspend operator fun invoke(): List<String> {
        val targetExams = userPrefRepoImpl.getUserTargetExams()
        Log.d("GetUserTargetsUC", "Fetched target exams: $targetExams")
        return targetExams
    }

}