package com.nabssam.bestbook.presentation.ui.quiz


import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun QuizCategoryScreen(
    modifier: Modifier = Modifier,
    moveToMCQ: (chapterId: String) -> Unit,
    state: examUiState,
    viewmodel: ExamViewModel,
    onAction: (QuizScreen) -> Unit,

//    quizId: String,
//    navController: NavController
) {

////    LaunchedEffect(subjectId) {
////        viewModel.fetchAllChapters(subjectId)
////    }
//    val chapter = mutableListOf("");
//    repeat(10) {
//        chapter.add("Chapter $it")
//
//    }

    if (state.chapters.isNotEmpty()) {
        Box(
            contentAlignment = Alignment.TopCenter,
        ) {
            LazyColumn(modifier.fillMaxSize()) {
                items(state.chapters.size) {
                    QuizListCard(it, moveToMCQ, state, viewmodel,onAction)
                }
            }
        }
    }
}

@Composable
fun QuizListCard(
    index: Int,
    moveToMCQ: (chapterId: String) -> Unit,
    state: examUiState,
    viewmodel: ExamViewModel,
    onAction: (QuizScreen) -> Unit
) {
    Card(
        modifier = Modifier
            .clickable {
                Log.d("QuizListCard", "QuizListCard: ${state.chapters[index]}")
//                viewmodel.fetAllQuizzes(state.chapters[index]._id)
                onAction(QuizScreen.fetchQuiz(state.chapters[index]._id))
                moveToMCQ(state.chapters[index]._id)

            }
            .fillMaxWidth()
            .height(90.dp)
            .padding(8.dp),
        shape = RoundedCornerShape(20.dp),

        ) {
        Box(
            Modifier
                .fillMaxSize()
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp), contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)


            ) {

                Text(
                    text = "chapter ${index + 1}",
                    fontSize = 24.sp,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowRight,
                    contentDescription = "array icon",
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    }
}
