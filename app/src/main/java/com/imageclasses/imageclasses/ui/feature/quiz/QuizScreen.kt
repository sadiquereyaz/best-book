package com.imageclasses.imageclasses.ui.feature.quiz



import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.serialization.Serializable

@Serializable
data class QuizRoute(val quizId: String, val categoryId:String)

@Composable
fun QuizScreen(
        modifier: Modifier = Modifier,
        quizId: String,
        categoryId: String
    ){

    Text(text = "Quiz Screen: $quizId , $categoryId")
}