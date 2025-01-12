package com.nabssam.bestbook.data.remote.api

import com.nabssam.bestbook.data.remote.dto.ChapterResponse
import com.nabssam.bestbook.data.remote.dto.ExamResponse
import com.nabssam.bestbook.data.remote.dto.QuizResponse
import com.nabssam.bestbook.data.remote.dto.SubjectResponse
import retrofit2.Response

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ExamApi {
    @GET("api/exams/getallexams")
    suspend fun getAllExams(): Response<ExamResponse>

    @GET("api/subjects/getallsubjects/{examId}")
    suspend fun getAllSubjectsInExam(@Path("examId") examId: String):Response<SubjectResponse>

    @GET("api/chapters/getchapters/{subjectId}")
    suspend fun getAllChaptersInSubject(@Path("subjectId") subjectId: String):Response<ChapterResponse>

    @GET("api/quizzes/getquizbyid/{chapterId}")
    suspend fun getAllQuizzesInChapter(@Path("chapterId") chapterId: String):Response<QuizResponse>

    @GET("api/v1/ecommerce/categories")
    suspend fun getAllCategory(
        @Query("page") page:Int = 1,
        @Query("limit") limit:Int = 5
    ):Response<>
}
