package com.nabssam.bestbook.data.remote.dto

data class ChapterResponse(
    val chapters: List<Chapter>,
    val success: Boolean
)
data class Chapter(
    val _id: String,
    val name: String,
    val subject: String
)