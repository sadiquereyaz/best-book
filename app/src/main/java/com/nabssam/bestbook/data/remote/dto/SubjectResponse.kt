package com.nabssam.bestbook.data.remote.dto

data class SubjectResponse(
    val message: String,
    val subjects: List<Subject>,
    val success: Boolean
)

data class Subject(
    val _id: String,
    val exam: String,
    val name: String
)