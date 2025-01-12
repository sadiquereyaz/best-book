package com.nabssam.bestbook.presentation.ui.quiz

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nabssam.bestbook.R
import com.nabssam.bestbook.presentation.ui.components.FullScreenProgressIndicator
import kotlinx.serialization.Serializable

@Serializable
data class QuizRoute(val quizId: String, val categoryId: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MCQScreen(
    modifier: Modifier = Modifier,
    state: examUiState,

) {
    val context = LocalContext.current

    Log.d("MCQScreen", "MCQScreen state: ${QuizScreen.fetchQuiz().chapterId}")
    var currentQuestionIndex by remember { mutableStateOf(0) }
    var selectedOption by remember { mutableStateOf<Int?>(null) }
    var correctAnswers by remember { mutableStateOf(0) }

    // Check if quizzes are loaded
    val quizList = state.quizzes
    val isQuizListAvailable = quizList.isNotEmpty()

    // Loading Indicator
    if (state.isLoading) {
        FullScreenProgressIndicator(
            modifier = Modifier.height(dimensionResource(R.dimen.banner_height))
        )
        return
    }

    // Main Content
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        if (isQuizListAvailable) {
            // Quiz Card
            Card(
                modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(8.dp)
            ) {
                Column(
                    modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Timer and Question Number Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Q ${currentQuestionIndex + 1}",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Start,
                        )
                        Text(
                            text = "Timer", // Replace with actual timer implementation
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.End
                        )
                    }

                    // Question Text
                    Text(
                        text = quizList[0].questions[currentQuestionIndex].text,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Options
            quizList[0].questions[currentQuestionIndex].options.forEachIndexed { index, option ->
                OptionCard(
                    optionText = option.text,
                    isSelected = selectedOption == index,
                    onClick = {
                        selectedOption = index
                        // Check if answer is correct
                        if (option.isCorrect) {
                            correctAnswers++
                            Toast.makeText(context, "Correct Answer!", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "Wrong Answer!", Toast.LENGTH_SHORT).show()
                        }
                    }
                )
            }

            // Bottom Bar for Navigation
            BottomBar(
                onPrevClick = { if (currentQuestionIndex > 0) currentQuestionIndex-- },
                onNextClick = {
                    if (currentQuestionIndex < quizList[0].questions.size - 1) {
                        currentQuestionIndex++
                        selectedOption = null // Reset selection for the next question
                    } else {
                        Toast.makeText(context, "Quiz Completed", Toast.LENGTH_SHORT).show()
                    }
                }
            )
        } else {
            // No Quiz Available Message
            Text(
                text = "No quizzes available.",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxSize().padding(16.dp)
            )
        }
    }
}

@Composable
fun OptionCard(
    optionText: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 100.dp)
            .padding(8.dp)
            .border(
                width = 2.dp,
                color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray,
                shape = RoundedCornerShape(10.dp)
            )
            .background(
                color = if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.2f) else Color.Transparent,
                shape = RoundedCornerShape(10.dp)
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = optionText,
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
fun BottomBar(onPrevClick: () -> Unit, onNextClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(onClick = onPrevClick) {
            Text(text = "Previous")
        }
        Button(onClick = onNextClick) {
            Text(text = "Next")
        }
    }
}
