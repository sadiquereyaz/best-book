package com.nabssam.bestbook.presentation.ui.quiz


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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun QuizCategoryScreen(
    modifier: Modifier = Modifier,
    moveToMCQ: () -> Unit,

//    quizId: String,
//    navController: NavController
) {
    val viewModel = hiltViewModel<ExamViewModel>()
    val state by viewModel.uiState.collectAsState()

//    LaunchedEffect(subjectId) {
//        viewModel.fetchAllChapters(subjectId)
//    }
    val chapter = mutableListOf("");
    repeat(10) {
        chapter.add("Chapter $it")

    }
  if(state.chapters.isNotEmpty()){
      Box(
          contentAlignment = Alignment.TopCenter,
      ) {
          LazyColumn(modifier.fillMaxSize()) {
              items(state.chapters.size) {
                  QuizListCard(it, moveToMCQ)
              }
          }
      }
  }
}

@Composable
fun QuizListCard(index: Int, moveToMCQ: () -> Unit) {
    Card(
        modifier = Modifier.clickable { moveToMCQ() }
            .fillMaxWidth()
            .height(90.dp)
            .padding(8.dp)
            ,
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
                    .clickable {

                    }

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
