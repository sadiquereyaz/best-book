package com.nabssam.bestbook.presentation.ui.contest

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import com.nabssam.bestbook.presentation.ui.components.GradientButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nabssam.bestbook.presentation.ui.contest.composable.ContestHeader
import com.nabssam.bestbook.presentation.ui.contest.composable.CustomLinearProgressIndicator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContestScreen(
    modifier: Modifier = Modifier
) {
    val sections = listOf("Motion", "Force and Laws", "Oscillation", "Newtons Laws of Motion")
    val options = listOf(
        "Motion",
        "Force and Laws.Linear momentum and moment of force have the same dimensions. Force and Laws.",
        "Oscillation",
        "Newtons Laws of Motion"
    )
    var showAnswer by remember { mutableStateOf(false) }
    val tabScrollState = rememberScrollState()
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        // Header Section
        ContestHeader()

        // Tabs Section
        PrimaryScrollableTabRow(
            scrollState = tabScrollState,
            modifier = Modifier,
            selectedTabIndex = 0,
            edgePadding = 0.dp,
        ) {
            sections.forEachIndexed { index, title ->
                Tab(selected = index == 0,
                    onClick = { /* Tab clicked */ },
                    text = { Text(text = title) })
            }
        }
        LazyRow(
            modifier = Modifier.padding(horizontal = 4.dp)
        ) {
            item {
                repeat(101) {
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 4.dp, vertical = 8.dp)
                            .size(36.dp)
                            .border(
                                0.2.dp,
                                MaterialTheme.colorScheme.onSecondaryContainer,
                                shape = CircleShape
                            )
                            .clip(CircleShape)
                            .background(
                                color =
                                //MaterialTheme.colorScheme.surfaceBright
                                MaterialTheme.colorScheme.surfaceContainer
                            ), contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = it.toString(),
                            modifier = Modifier,
                        )
                    }
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Single correct",
                modifier = Modifier.padding(horizontal = 16.dp),
                color = MaterialTheme.colorScheme.secondary
            )
            Text(
                text = "+4 | -1", modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .border(
                        0.5.dp,
                        MaterialTheme.colorScheme.secondary,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(horizontal = 8.dp), color = MaterialTheme.colorScheme.error
            )
        }
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Question Section
            item {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                        .background(
                            color = MaterialTheme.colorScheme.surfaceBright,
                            shape = RoundedCornerShape(12.dp)
                        )

                ) {
                    Text(
                        modifier = Modifier.padding(16.dp), text = """
                    Given below are two statements:
                    
                    Statement (I): Planck's constant and angular momentum have the same dimensions.
                    
                    Statement (II): Linear momentum and moment of force have the same dimensions.
                    
                    In light of the above statements, choose the correct answer from the options given below:
                """.trimIndent(), fontSize = 16.sp
                    )
                }
            }
            //options
            item {
                options.forEach {
                    var progress by remember { mutableFloatStateOf(0.1f) }
                    val animatedProgress by animateFloatAsState(
                        targetValue = progress,
                        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
                    )
                    CustomLinearProgressIndicator(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth()
                            .padding(12.dp)
//                            .height(80.dp)

//                            .heightIn(
//                                min = dimensionResource(
//                                    R.dimen.option_min_height
//                                )
//                            )
                        ,
                        progress = animatedProgress,
                        option = it,
                        showAnswer = showAnswer,
                        isCorrect = false,
                        onOptionClick = { showAnswer != showAnswer}
                    )
                   /* Slider(
                        modifier =
                        Modifier.width(300.dp),
                        value = progress, valueRange = 0f..1f,
                        onValueChange = {
                            progress = it
                        },
                    )*/
                }
            }
            item{
                Spacer(modifier = Modifier.height(16.dp))

                // Navigation Buttons
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedButton(
                        onClick = { /* Clear response */ },
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Icon(Icons.Default.ArrowBack, "prev")
                    }
                    GradientButton(onClick = { showAnswer = !showAnswer }) {
                        Text(text = "Show Answer")
                    }
                    OutlinedButton(
                        onClick = { /* Clear response */ },
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Icon(Icons.Default.ArrowForward, "prev")
                    }
                }
            }
        }
    }
}


