package com.nabssam.bestbook.presentation.ui.quiz

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.serialization.Serializable

/*@Serializable
data class AllQuizListRoute(val examId: String)*/
@Serializable
data class AllQuizListRoute(val examId: String? = null) {
    override fun toString(): String {
        return "all_quiz_list_route/${examId ?: ""}"
    }
}

@Composable
fun AllQuizListScreen(
    modifier: Modifier = Modifier,
    examId: String
){
    Text(text = "Quiz List Screen: $examId")
}