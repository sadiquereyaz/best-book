package com.nabssam.bestbook.presentation.ui.contest.composable

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.nabssam.bestbook.R

@Composable
fun CustomLinearProgressIndicator(
    modifier: Modifier = Modifier,
    progress: Float,
    clipShape: Shape = RoundedCornerShape(16.dp),
    option: String,
    showAnswer: Boolean = true,
    isCorrect : Boolean = false,
    onOptionClick: ()->Unit
) {
    val progressColor = if (isCorrect) Color.Green else Color.Red
    if (showAnswer)
    Box(
        modifier = modifier
            .zIndex(0f)
            .clip(clipShape)
            .background(progressColor.copy(0.1f))
            .wrapContentSize()
    ) {

            Box(
                modifier = Modifier
//                    .zIndex(3f)
                    .background(progressColor.copy(0.3f))
                    .fillMaxHeight(0.5f)
                    .fillMaxWidth(progress)
//                    .matchParentSize()
                    .animateContentSize()
                    .heightIn(
                        min = 8.dp
                    )
            )
        Row(
            modifier = Modifier
                .zIndex(5f)
                .fillMaxSize()
                //.background(Color.LightGray)
                .heightIn(
                    min = dimensionResource(
                        R.dimen.option_min_height
                    )
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier
                    .zIndex(5f)
                    .fillMaxHeight()
                    .align(Alignment.CenterVertically)
                    .padding(8.dp),
                text = option,
            )
            Text(
                modifier = Modifier
                    .zIndex(0f)
                    .fillMaxHeight()
                    .align(Alignment.CenterVertically)
                    .padding(horizontal = 4.dp),
                text = "${progress.times(100).toInt()}%",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface.copy(0.5f)
            )
        }
    }
    else{
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp)
                .clickable {
                    //onEvent(AuthEvent.UpdateUserTargetExam(it))
                    onOptionClick()
                }
                .border(
                    if (/*state.userTargetExams.contains(it)*/ false) BorderStroke(
                        width = 2.dp, color = MaterialTheme.colorScheme.primary
                    )
                    else BorderStroke(
                        width = 1.dp, color = MaterialTheme.colorScheme.secondary
                    ),
                    shape = RoundedCornerShape(15.dp)
                )
                .clip(RoundedCornerShape(15.dp))
                .background(
                    if (/*state.userTargetExams.contains(it)*/false) {
                        MaterialTheme.colorScheme.primaryContainer
                    } else {
                        MaterialTheme.colorScheme.surface
                    }
                )
                .heightIn(min = dimensionResource(R.dimen.option_min_height)),
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = option /*modifier = Modifier.padding(16.dp)*/)

            }
        }
    }
}