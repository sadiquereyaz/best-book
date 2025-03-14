package com.nabssam.bestbook.data.repository

import android.util.Log
import com.nabssam.bestbook.data.mapper.ExamMapper
import com.nabssam.bestbook.data.remote.api.ExamApi
import com.nabssam.bestbook.data.remote.dto.Chapter
import com.nabssam.bestbook.data.remote.dto.Quize
import com.nabssam.bestbook.data.remote.dto.Subject
import com.nabssam.bestbook.domain.model.Exam
import com.nabssam.bestbook.domain.repository.ExamRepository
import com.nabssam.bestbook.utils.Resource
import com.nabssam.bestbook.utils.Standard
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ExamRepositoryImpl @Inject constructor(
    private val examApi: ExamApi,
    val mapper: ExamMapper
): ExamRepository {
    override fun fetchAllExams(): Flow<Resource<List<Exam>>> = flow {
        emit(Resource.Loading())
        try {
            val response = examApi.getAllExams()
            Log.d("examRepository", "fetchAllExams:${response.body()} ")
            if (response.isSuccessful) {
                val examResponse = response.body()
                if (examResponse != null) {
                    val exams = examResponse.exams.map {
                        mapper.dtoToDomain(it)
                    }
                    emit(Resource.Success(data = exams))
                } else {
                    emit(Resource.Error(message = "no exam found"))
                }

            }
        } catch (e: Exception) {
            emit(Resource.Error(message = e.message))
        }
    }

    override fun fetchAllTarget(): Flow<Resource<List<String>>> = flow {
        emit(Resource.Loading())

        try {
            val response = examApi.getAllTargetExam()
            if (response.isSuccessful) {
                response.body()?.let {
                    /* val targetList: List<String> = it.classExamList.forEach{ std->
                         mapper.dtoToDomaint(std)
                     }

                     emit(Resource.Success(data = targetList))*/
                } ?: emit(Resource.Error(message = "Empty response"))
            } else {
                emit(Resource.Error("Error: ${response.code()} - ${response.message()}"))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An unexpected error occurred"))
        }
    }

    override fun fetchAllStandard1(): Flow<Resource<List<Standard>>> = flow {
        emit(Resource.Loading())
        try {
            val response = examApi.getAllTargetExam()
            if (response.isSuccessful) {
                val examResponse = response.body()
                if (examResponse != null) {
                    val exams = examResponse.classExamList
                        .map {
                            mapper.dtoToDomainFinal(it)
                        }
                    emit(Resource.Success(data = exams))
                } else {
                    emit(Resource.Error(message = "no exam found"))
                }
            }
        } catch (e: Exception) {
            emit(Resource.Error(message = e.message))
        }
    }

    override suspend fun fetchAllStandard(): Result<List<Standard>> {
        return try {
            val response = examApi.getAllTargetExam()
            if (response.isSuccessful) {
                val examResponse = response.body()
                if (examResponse != null) {
                    val exams = examResponse.classExamList
                        .map {
                            mapper.dtoToDomainFinal(it)
                        }
                    Result.success(exams)
                } else {
                    Result.failure(Exception("no exam found"))
                }
            } else Result.failure(Exception("Error: ${response.code()} - ${response.message()}"))
        } catch (e: Exception) {
            Result.failure(Exception(e.message))
        }
    }

    override fun fetchAllSubjects(examId: String): Flow<Resource<List<Subject>>> = flow {
        emit(Resource.Loading())
        try {
            val response = examApi.getAllSubjectsInExam(examId);
            if (response.isSuccessful) {
                val subjectResponse = response.body()
                if (subjectResponse != null) {
                    val subjects = subjectResponse.subjects
                    emit(Resource.Success(data = subjects))
                } else {
                    emit((Resource.Error(message = "no subject found")))
                }
            }
        } catch (e: Exception) {
            emit(Resource.Error(message = e.message))

        }
    }

    override fun fetchAllChapters(subjectId: String): Flow<Resource<List<Chapter>>> = flow {
        emit(Resource.Loading())
        try {

            val response = examApi.getAllChaptersInSubject(subjectId)
            if (response.isSuccessful) {
                val chapterResponse = response.body()
                if (chapterResponse != null) {
                    val chapters = chapterResponse.chapters
                    emit(Resource.Success(data = chapters))
                } else {
                    emit(Resource.Error(message = "no chapter found"))
                }
            }
        } catch (e: Exception) {
            emit(Resource.Error(message = e.message))
        }
    }

    override fun fetchAllQuizzes(chapterId: String): Flow<Resource<List<Quize>>> = flow {

        emit(Resource.Loading())

        try {
            val response = examApi.getAllQuizzesInChapter(chapterId)
            if (response.isSuccessful) {
                val quizResponse = response.body()
                if (quizResponse != null) {
                    val quizzes = quizResponse.quizes
                    emit(Resource.Success(data = quizzes))
                }
            }
        } catch (e: Exception) {
            emit(Resource.Error(message = e.message))
        }
//        TODO() make it correct for more then one quiz in a chapter
    }
}