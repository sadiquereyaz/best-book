package com.imageclasses.imageclasses.ui.feature.quizCategory



import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.serialization.Serializable

@Serializable
data class QuizCategoryRoute(val quizId: String)

@Composable
fun QuizCategoryScreen(
    modifier: Modifier = Modifier,
    quizId: String
    ){
    Text(text = "Quiz Category Screen: $quizId")
}