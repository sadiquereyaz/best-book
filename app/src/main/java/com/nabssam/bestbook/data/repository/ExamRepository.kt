package com.nabssam.bestbook.data.repository

import com.nabssam.bestbook.data.mapper.ExamMapper
import com.nabssam.bestbook.data.remote.api.ExamApi
import com.nabssam.bestbook.data.remote.dto.ExamDto
import com.nabssam.bestbook.domain.model.Exam
import com.nabssam.bestbook.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ExamRepository @Inject constructor(
    val examApi: ExamApi,
    val mapper: ExamMapper
) {
    suspend fun fetchAllExams(): Flow<Resource<List<Exam>>> = flow {
        emit(Resource.Loading())
        try {
            val response = examApi.getAllExams()
            if (response.isSuccessful) {
                val examResponse = response.body()
                if (examResponse != null) {
                    val exams = examResponse.exams.map {
                        mapper.dtoToDomain(it)
                    }

                    emit(Resource.Success(data = exams))
                }
                else{
                    emit(Resource.Error(message = "no exam found"))
                }

            }
        } catch (e: Exception) {
            emit(Resource.Error(message = e.message))
        }
    }
}