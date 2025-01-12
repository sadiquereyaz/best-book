package com.nabssam.bestbook.presentation.ui.quiz

sealed class QuizScreen (){
    data class fetchChapters(val subjectId:String?=null) : QuizScreen()
    data class fetchSubjects(var examId:String?=null) : QuizScreen()

    data class fetchQuiz(val chapterId:String?=null) : QuizScreen()

}
