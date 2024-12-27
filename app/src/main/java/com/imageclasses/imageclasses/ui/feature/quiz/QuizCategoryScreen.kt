package com.imageclasses.imageclasses.ui.feature.quiz


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.imageclasses.imageclasses.ui.theme.backgroundDark
import kotlinx.serialization.Serializable

@Serializable
data class QuizCategoryRoute(val quizId: String)

@Composable
fun QuizCategoryScreen(
    modifier: Modifier = Modifier,
//    quizId: String
) {
    val chapter = mutableListOf("");
    repeat(10) {
        chapter.add("Chapter $it")

    }
    Box(
        contentAlignment = Alignment.TopCenter,
    ) {
        LazyColumn(modifier.fillMaxSize()) {
            items(chapter.size) {
                QuizListCard(it)
            }
        }
    }
}

@Composable
fun QuizListCard(index: Int) {
    Card(
        modifier = Modifier
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
