package com.nabssam.bestbook.data.repository

import android.util.Log
import com.nabssam.bestbook.data.mapper.ExamMapper
import com.nabssam.bestbook.data.remote.api.ExamApi
import com.nabssam.bestbook.data.remote.dto.Chapter
import com.nabssam.bestbook.data.remote.dto.Quize
import com.nabssam.bestbook.data.remote.dto.Subject
import com.nabssam.bestbook.domain.model.Exam
import com.nabssam.bestbook.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ExamRepository @Inject constructor(
    private val examApi: ExamApi,
    val mapper: ExamMapper
) {
     fun fetchAllExams(): Flow<Resource<List<Exam>>> = flow {
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
                }
                else{
                    emit(Resource.Error(message = "no exam found"))
                }

            }
        } catch (e: Exception) {
            emit(Resource.Error(message = e.message))
        }
    }
     fun fetchAllCategories(): Flow<Resource<List<Exam>>> = flow {
        /*emit(Resource.Loading())
        try {
            val response = examApi.getAllCategory()
            if (response.isSuccessful) {
                val examResponse = response.body()
                if (examResponse != null) {
                    val exams = examResponse..map {
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
        }*/
    }

    fun fetchAllSubjects(examId:String): Flow<Resource<List<Subject>>> = flow {
        emit(Resource.Loading())
        try {
            val response = examApi.getAllSubjectsInExam(examId);
            if (response.isSuccessful) {
                val subjectResponse = response.body()
                if(subjectResponse != null){
                    val subjects = subjectResponse.subjects
                    emit(Resource.Success(data = subjects))
                }else{
                    emit((Resource.Error(message = "no subject found")))
                }
            }
        }catch (e:Exception){
            emit(Resource.Error(message = e.message))

        }
    }

    fun fetchAllChapters(subjectId:String): Flow<Resource<List<Chapter>>> = flow {
        emit(Resource.Loading())
        try {

            val response = examApi.getAllChaptersInSubject(subjectId)
            if(response.isSuccessful){
                val chapterResponse = response.body()
                if(chapterResponse != null){
                    val chapters = chapterResponse.chapters
                    emit(Resource.Success(data = chapters))
                }else{
                    emit(Resource.Error(message = "no chapter found"))
                }
            }
        }catch (e:Exception){
            emit(Resource.Error(message = e.message))
        }
    }

    fun fetchAllQuizzes(chapterId:String): Flow<Resource<List<Quize>>> = flow {

        emit(Resource.Loading())

        try {
            val response = examApi.getAllQuizzesInChapter(chapterId)
            if(response.isSuccessful){
                val quizResponse = response.body()
                if(quizResponse != null){
                    val quizzes = quizResponse.quizes
                    emit(Resource.Success(data = quizzes))
                }
            }
        }catch (e:Exception){
            emit(Resource.Error(message = e.message))
    }
//        TODO() make it correct for more then one quiz in a chapter
    }
}