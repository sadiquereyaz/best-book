package com.nabssam.bestbook.presentation.ui.account.profile

import androidx.annotation.ColorInt
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.Cyan
import androidx.compose.ui.graphics.Color.Companion.Magenta
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.nabssam.bestbook.R

@Composable
fun PercentageRoundedCornerBox(
    modifier: Modifier = Modifier,
    topCornerPercentage: Float, // Percentage for top corners (0.0f to 1.0f)
    bottomCornerPercentage: Float, // Percentage for bottom corners (0.0f to 1.0f)
    content: @Composable () -> Unit
) {
    val gradientColors = listOf(
        Color(0xFF1E3A8A),  // from-blue-900
        Color(0xFF1E40AF),  // via-blue-800
        Color(0xFF6B21A8)   // to-purple-800
    )
    var componentSize by remember { mutableStateOf(IntSize.Zero) }
    val density = LocalDensity.current

    val topCornerRadius: Dp = remember(componentSize) {
        with(density) { (componentSize.width * topCornerPercentage).toDp() }
    }
    val bottomCornerRadius: Dp = remember(componentSize) {
        with(density) { (componentSize.width * bottomCornerPercentage).toDp() }
    }

    Box(
        modifier = modifier
            .onSizeChanged { componentSize = it }
            .clip(
                RoundedCornerShape(
                    topStart = topCornerRadius,
                    topEnd = topCornerRadius,
                    bottomStart = bottomCornerRadius,
                    bottomEnd = bottomCornerRadius
                )
            )
            .background(
                color = Color.Black
            )
    ) {
        content()
    }
}

@Composable
fun ProfileScreen(modifier: Modifier = Modifier) {
    val gradientColors = listOf(Cyan, Blue, Magenta /*...*/)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 56.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.25f)
//                .background(
//                    color = Black, shape = RoundedCornerShape(
//                        topStart = 0.dp,
//                        bottomStart = 100.dp,
//                        topEnd = 0.dp,
//                        bottomEnd = 100.dp
//
//
//                    )
//                ),
            , contentAlignment = Alignment.BottomCenter
        ) {
            PercentageRoundedCornerBox(
                modifier = Modifier.fillMaxSize(),
                topCornerPercentage = 0.0f, // 10% of width for top corners
                bottomCornerPercentage = 0.5f // 30% of width for bottom corners
            ) {
                Text(text = "Rounded Corners with Percentages", modifier = Modifier.padding(16.dp))
            }

               Image(
                   painter = painterResource(id = R.drawable.profile_placeholder),
                   contentDescription = "",
                   modifier

                       .align(Alignment.BottomCenter) // Align to bottom center of the parent
                       .offset(y = (35).dp) // Adjust vertical position to center of rounded corner
                       .clip(CircleShape)

                       .padding(24.dp)
                       .size(56.dp),
                   colorFilter = ColorFilter.tint(color = Blue)

               )
        }
    }
}