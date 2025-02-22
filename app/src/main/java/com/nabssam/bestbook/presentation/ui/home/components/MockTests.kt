package com.nabssam.bestbook.presentation.ui.home.components

import android.content.res.Configuration.UI_MODE_TYPE_NORMAL
import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nabssam.bestbook.R
import java.math.BigDecimal
import kotlin.math.ceil

@Composable
fun MockTests(
    modifier: Modifier = Modifier,
    navigateToMock: (String) -> Unit
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val cardWidth = dimensionResource(R.dimen.book_width_home)
    val spacing = 8.dp
    val itemPerRow = (screenWidth / (cardWidth + spacing)).toInt()
    var isExpanded by remember { mutableStateOf(false) }
    // Filter items based on isExpanded state
    val visibleItems = if (isExpanded) dummyMockTests else dummyMockTests.take(itemPerRow)
    val totalRows = ceil(dummyMockTests.size.toDouble() / itemPerRow).toInt()
    val totalHeight = dimensionResource(R.dimen.book_height_home).plus(spacing).times(totalRows)

    Column(modifier = modifier) {
        // Header Row with title and expand/collapse button
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Contests", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.weight(1f))
            TextButton(
                contentPadding = PaddingValues(0.dp),
                onClick = { isExpanded = !isExpanded } // Toggle expand/collapse state
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (isExpanded) "Collapse" else "View all",
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        }

        LazyVerticalGrid(
            modifier = Modifier.heightIn( max = totalHeight).animateContentSize(),
            columns = GridCells.Adaptive(minSize = dimensionResource(R.dimen.book_width_home)),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            userScrollEnabled = false
        ) {
            items(visibleItems) { item ->
                Box(
                    modifier = Modifier
                        .height(dimensionResource(R.dimen.book_height_home))
                        .width(dimensionResource(R.dimen.book_width_home))
                        .background(
                            brush = Brush.verticalGradient(
                                listOf(
                                    item.color.copy(alpha = 0.03f),
                                    item.color.copy(alpha = 0.2f)
                                )
                            ), shape = RoundedCornerShape(16.dp)
                        )
                        .clip(RoundedCornerShape(16.dp))
                        .clickable { navigateToMock(item.id) }

                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp),
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = item.title,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Start,
                            color = item.color.copy(alpha = 0.7f)
                        )
                        Text(
                            text = item.subtitle,
                            fontSize = 12.sp,
                            textAlign = TextAlign.Start,
                            color = item.color,
                            lineHeight = 16.sp
                        )
                    }
                }
            }

        }
    }
}

data class MockHomeModel(
    val id: String,
    val title: String,
    val color: Color,
    val subtitle: String = "More information about the mock",
)

val dummyMockTests = listOf(
    MockHomeModel("5", "Mock Test 5", Color(0xFF006500)),
    MockHomeModel("8", "XI-Entrance BHU, Full Syllabus", Color.DarkGray),
    MockHomeModel("1", "JEE Main", Color(0xFF7C000D)),
    MockHomeModel("5", "Mock Test 5", Color(0xFF5B0065)),
    MockHomeModel("3", "XI-Entrance AMU", Color.Blue),
)

@Preview(
    showBackground = true,
    showSystemUi = true,
    uiMode = UI_MODE_TYPE_NORMAL,
    device = Devices.DEFAULT
)
@Composable
fun MockTestsPreview() {
    Scaffold {
        MockTests(modifier = Modifier.padding(it), navigateToMock = {})
    }
}