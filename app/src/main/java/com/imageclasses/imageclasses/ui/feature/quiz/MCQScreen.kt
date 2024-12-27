package com.imageclasses.imageclasses.ui.feature.quiz

import Quiz
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import getQuestionList

@Composable
fun MCQScreen(modifier: Modifier = Modifier, quizList: List<Quiz> = getQuestionList()) {
    val subjects = listOf("English", "General Knowledge", "Math", "Science")
    var selectedTab by remember { mutableStateOf(0) }
    val pagerState = rememberPagerState { subjects.size }
    var selectedOption by remember { mutableStateOf<Int?>(null) }
    var currentQuestionIndex by remember { mutableStateOf(0) }
    var correctAnswers by remember { mutableStateOf(0) }

    LaunchedEffect(selectedTab) {
        pagerState.animateScrollToPage(selectedTab)
        currentQuestionIndex = 0
        correctAnswers = 0
        selectedOption = null
    }

    LaunchedEffect(pagerState.currentPage) {
        if (!pagerState.isScrollInProgress) {
            selectedTab = pagerState.currentPage
            currentQuestionIndex = 0
            correctAnswers = 0
            selectedOption = null
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 56.dp),
    ) {
        TabRow(selectedTabIndex = selectedTab) {
            subjects.forEachIndexed { index, item ->
                Tab(
                    selected = index == selectedTab,
                    onClick = { selectedTab = index },
                    text = { Text(text = item, color = MaterialTheme.colorScheme.onSurface) },
                    unselectedContentColor = MaterialTheme.colorScheme.surface
                )
            }
        }

        HorizontalPager(
            userScrollEnabled = false,
            state = pagerState,

            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) { page ->
//            val filteredQuizList = quizList.filter { it.subject == subjects[page] }
//
//            if (currentQuestionIndex < filteredQuizList.size) {
//                val quiz = filteredQuizList[currentQuestionIndex]
//                Column(
//                    horizontalAlignment = Alignment.CenterHorizontally,
//                    modifier = Modifier.padding(16.dp)
//                ) {
//                    QuestionCard(
//                        quiz = quiz,
//                        currentQuestionIndex = currentQuestionIndex,
//                        selectedOption = selectedOption,
//                        onOptionSelected = { selectedOption = it }
//                    )
//
//                    Spacer(modifier = Modifier.height(16.dp))
//
//                    Button(
//                        onClick = {
//                            // Check if the selected option is correct
//                            if (selectedOption == quiz.correctAnswerIndex) {
//                                correctAnswers++
//                            }
//
//                            // Move to next question and reset selected option
//                            currentQuestionIndex++
//                            selectedOption = null
//                        },
//                        modifier = Modifier.align(Alignment.CenterHorizontally)
//                    ) {
//                        Text("Next")
//                    }
//                }
//            } else {
//                // Display result
//                Column(
//                    horizontalAlignment = Alignment.CenterHorizontally,
//                    modifier = Modifier.padding(16.dp)
//                ) {
//                    Text(
//                        text = "Quiz completed! Correct answers: $correctAnswers / ${filteredQuizList.size}",
//                        color = Color.Green,
//                        modifier = Modifier.padding(bottom = 16.dp)
//                    )
//
//                    Button(onClick = {
//                        // Reset the quiz for the current subject
//                        currentQuestionIndex = 0
//                        correctAnswers = 0
//                        selectedOption = null
//                    }) {
//                        Text("Restart")
//                    }
//                }
//            }

            QuizCategoryScreen()
        }
    }
}

@Composable
fun QuestionCard(
    quiz: Quiz,
    currentQuestionIndex: Int,
    selectedOption: Int?,
    onOptionSelected: (Int) -> Unit
) {
   Box(contentAlignment = Alignment.TopCenter){
       Column(
           modifier = Modifier.fillMaxWidth(),
           verticalArrangement = Arrangement.spacedBy(8.dp),
           horizontalAlignment = Alignment.CenterHorizontally
       ) {
           // Question Text
           val questionNumber = currentQuestionIndex + 1
           Text(
               text = "$questionNumber. ${quiz.question}",
               color = Color.Black,
               modifier = Modifier.padding(bottom = 4.dp)
           )

           // Options
           quiz.options.forEachIndexed { index, option ->
               Card {
                   Row(
                       verticalAlignment = Alignment.CenterVertically,
                       modifier = Modifier
                           .fillMaxWidth()
                           .padding(horizontal = 8.dp)
                   ) {
                       RadioButton(
                           selected = selectedOption == index,
                           onClick = { onOptionSelected(index) }
                       )
                       Text(text = option)
                   }
               }
           }
       }
   }
}


@Preview
@Composable
private fun MCQScreenPreview() {
    MCQScreen()
}
