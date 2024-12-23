package com.imageclasses.imageclasses.ui.feature.quizList

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.serialization.Serializable

@Serializable
data class AllQuizListRoute(val examId: String)

@Composable
fun AllQuizListScreen(
    modifier: Modifier = Modifier,
    examId: String
){
    Text(text = "Quiz List Screen: $examId")
}