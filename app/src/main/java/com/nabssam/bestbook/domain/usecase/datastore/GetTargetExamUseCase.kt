package com.nabssam.bestbook.domain.usecase.datastore

import com.nabssam.bestbook.data.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTargetExamUseCase @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) {
    operator fun invoke(): Flow<String> = userPreferencesRepository.targetExam
}