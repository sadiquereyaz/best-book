package com.nabssam.bestbook.data.remote.dto

data class ExamResponse(
    val exams: List<ExamDto>,
    val message: String,
    val success: Boolean
)



data class ExamDto(
    val __v: Int,
    val _id: String,
    val createdAt: String,
    val description: String,
    val discount: Double,
    val name: String,
    val price: Int,
    val updatedAt: String
)