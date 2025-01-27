package com.nabssam.bestbook.presentation.ui.contest.composable

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
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
    progressColor: Color = Color.Green.copy(0.3f),
    backgroundColor: Color = Color.Green.copy(0.1f),
    clipShape: Shape = RoundedCornerShape(16.dp),
    option: String
) {
    Box(
        modifier = modifier
            .zIndex(0f)
            .clip(clipShape)
            .background(backgroundColor)
            .fillMaxHeight()
    ) {
        Box(
            modifier = Modifier
                .zIndex(3f)
                .background(progressColor)
                .fillMaxHeight()
                .fillMaxWidth(progress)
                .animateContentSize()
                /*.heightIn(
                    min = dimensionResource(
                        R.dimen.option_min_height
                    )
                )*/
        ) /*{
            Row(
                modifier = Modifier
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
                        .fillMaxHeight()
                        .align(Alignment.CenterVertically)
                        //.fillMaxSize()
                        .padding(horizontal = 8.dp),
                    text = option,
                )
                Text(
                    modifier = Modifier
                        // .fillMaxSize()
                        .padding(horizontal = 4.dp),
                    text = "${progress.times(100).toInt()}%",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface.copy(0.5f)
                )
            }
        }*/
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
                    .padding(horizontal = 8.dp),
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
}