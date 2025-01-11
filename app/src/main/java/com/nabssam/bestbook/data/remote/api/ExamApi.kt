package com.nabssam.bestbook.data.remote.api

import com.nabssam.bestbook.data.remote.dto.ExamDto
import com.nabssam.bestbook.data.remote.dto.ExamResponse
import retrofit2.Response

import retrofit2.http.GET

interface ExamApi {
    @GET("api/exams/getallexams")
    suspend fun getAllExams(): Response<ExamResponse>
}