package com.nabssam.bestbook.presentation.ui.quiz


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nabssam.bestbook.domain.model.Quiz
import kotlinx.serialization.Serializable
import kotlin.random.Random

@Serializable
data class QuizRoute(val quizId: String, val categoryId: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun MCQScreen(
    modifier: Modifier = Modifier
) {
   /* val quizList = getQuestionList()
    var currentQuestionIndex by remember { mutableStateOf(0) }

    var correctAnswers by remember { mutableStateOf(0) }



        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
//                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Card(
                modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(8.dp)

            ) {
                Column(
                    modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, top = 2.dp, end = 6.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Timer and Question Number Row
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp, end = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
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
                                text = "Timer",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Normal,
                                textAlign = TextAlign.End
                            )
                        }
                    }

                    // Question Text
                    Text(
                        text = quizList[currentQuestionIndex].question,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))
            OptionCard(quizList, currentQuestionIndex)
            BottomBar(
                onPrevClick = {if(currentQuestionIndex >=1) currentQuestionIndex-- }
                , onNextClick = {if (currentQuestionIndex < quizList.size - 1) currentQuestionIndex++})
        }
*/
    }


@Composable
private fun OptionCard(
    quizList: List<Quiz>,
    currentQuestionIndex: Int,

    ) {
    var selectedOption by remember { mutableStateOf<Int?>(null) }
    val text = 'A'
    val randomColor = Color(
        Random.nextInt(256), // Red
        Random.nextInt(256), // Green
        Random.nextInt(256)  // Blue
    )

    quizList[currentQuestionIndex].options.forEachIndexed { index, option ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 100.dp)
                .padding(8.dp)

                .border(
                    2.dp,
                    color = if (selectedOption == index) Color.Black else MaterialTheme.colorScheme.errorContainer,
                    shape = RoundedCornerShape(10.dp)
                )
                .background(
                    shape = RoundedCornerShape(10.dp),
                    color = if (selectedOption == index) MaterialTheme.colorScheme.errorContainer else Color.Transparent
                )
                .clickable {
                    selectedOption = index
                },
            contentAlignment = Alignment.Center
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()

            ) {
                Box(
                    modifier = Modifier
                        .padding(start = 20.dp)

                ) {
                    Text(
                        text = "${(text + index)}",
                        fontSize = 35.sp,
                        fontWeight = FontWeight.Medium,

                        modifier = Modifier.padding(8.dp) // Add padding if needed
                    )
                }

                Text(
                    modifier = Modifier.padding(start = 20.dp),
                    text = option,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Normal,

                    )

            }
        }
    }

}

@Composable
fun BottomBar(onPrevClick: () -> Unit, onNextClick: () -> Unit) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround // Distribute cards evenly
        ) {
            var isPrevSelected by remember { mutableStateOf(false) }
            var isNextSelected by remember { mutableStateOf(false) }

            ClickableCard(text = "Prev", isSelected=isPrevSelected){
                isPrevSelected=!isPrevSelected
                onPrevClick()
                isPrevSelected=!isPrevSelected
            }
            ClickableCard(text = "Next", isSelected = isNextSelected ) {
                // Handle Next click
                isNextSelected=!isNextSelected
                onNextClick()
                isNextSelected=!isNextSelected

            }
        }

}


@Composable
fun ClickableCard(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(8.dp)
            .border(
                width = 2.dp,
                color = if (isSelected) Color.Black else MaterialTheme.colorScheme.errorContainer , // Change border color
                shape = RoundedCornerShape(8.dp)
            )
            .height(60.dp)
            .width(75.dp),
            colors = cardColors( containerColor = if(isSelected) MaterialTheme.colorScheme.errorContainer else MaterialTheme.colorScheme.surface)
    ) {
        Text(
            text = text,
            modifier = Modifier
                .padding(8.dp)
//                .weight(1f)
                .align(Alignment.CenterHorizontally)
                , // Fill card width
            textAlign = TextAlign.Center // Center text
        )
    }
}