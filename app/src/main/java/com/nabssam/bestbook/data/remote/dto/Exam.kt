package com.nabssam.bestbook.data.remote.dto

import com.google.gson.annotations.SerializedName

data class StandardResponse(
    @SerializedName("allTargetExamsForClasses")
    val classExamList: List<AllTargetExamsForClass>,
    val message: String,
    val success: Boolean
)

data class AllTargetExamsForClass(
    val _id: String,
    @SerializedName("class") val standard: String,
    val targetExam: List<String>
)

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