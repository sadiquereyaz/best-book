package com.nabssam.bestbook.data.remote.dto

data class QuizResponse(
    val quizes: List<Quize>,
    val success: Boolean
)

data class Quize(
    val _id: String,
    val questions: List<Question>,
    val title: String
)


data class Question(
    val _id: String,
    val answerFig: Any,
    val difficulty: String,
    val explanation: String,
    val options: List<Option>,
    val questionFig: Any,
    val text: String,
    val year: Int
)


data class Option(
    val _id: String,
    val isCorrect: Boolean,
    val text: String
)