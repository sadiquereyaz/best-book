package com.treeto.treeto.ui.feature.home

import android.content.ClipData.Item
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.imageclasses.imageclasses.R
import com.imageclasses.imageclasses.ui.BottomNavigationMenu
import com.imageclasses.imageclasses.ui.components.AutoScrollingImagePager

data class CustomCard(
    val backgroundColor: Color,
    val onBackgroundColor: Color,
    val borderColor: Color
)

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier.fillMaxSize(),
    onAllBookSelect: (String) -> Unit,
    onNavigateToBook: (Int) -> Unit

) {
    // Display 4 items
    val pagerState = rememberPagerState(pageCount = { 4 })


    val customCardList = listOf(
        CustomCard(
            backgroundColor = Color(0x6FFFB3B3), // Light Red
            onBackgroundColor = Color(0xFF000000), // Black
            borderColor = Color(0x31FF8080) // Dark Red
        ),
        CustomCard(
            backgroundColor = Color(0xFFCCFFCC), // Light Green
            onBackgroundColor = Color(0xFF000000), // Black
            borderColor = Color(0x3E99FF99) // Dark Green
        ),
        CustomCard(
            backgroundColor = Color(0x40CCDDFF), // Light Blue
            onBackgroundColor = Color(0xFF000000), // White
            borderColor = Color(0x3E6699FF) // Dark Blue
        ),
        CustomCard(
            backgroundColor = Color(0x63FFD58F), // Light Yellow
            onBackgroundColor = Color(0xFF000000), // Black
            borderColor = Color(0x54FFFF99) // Dark Yellow
        ),
        CustomCard(
            backgroundColor = Color(0x5CFFCCFF), // Light Purple
            onBackgroundColor = Color(0xFF000000), // White
            borderColor = Color(0x7EFF99FF) // Dark Purple
        ),
    )

    Column(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AutoScrollingImagePager()
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Popular Books", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.weight(1f))
            TextButton(
                onClick = {}
            ) {
                Text(text = "View all", style = MaterialTheme.typography.labelLarge)
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        LazyRow(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            repeat(3) {
                item {
                    Box(
                        modifier = Modifier
                            .border(
                                width = 1.dp,
                                shape = RectangleShape,
                                color = Color.Black
                            )
                            .padding(6.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.book1),
                            contentDescription = null,
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier
                                .width(120.dp)
                                .height(150.dp)
                                .clip(shape = RoundedCornerShape(6.dp)),
                        )
                    }
                }
                item {
                    Box(
                        modifier = Modifier
                            .border(
                                width = 1.dp,
                                shape = RectangleShape,
                                color = Color.Black
                            )
                            .padding(6.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.book2),
                            contentDescription = null,
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier
                                .width(120.dp)
                                .height(150.dp)
                                .clip(shape = RoundedCornerShape(6.dp)),
                        )
                    }
                }
            }
        }

        // quiz
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Question Bank", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.weight(1f))
            TextButton(
                onClick = {}
            ) {
                Text(text = "View all", style = MaterialTheme.typography.labelLarge)
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        LazyHorizontalGrid(
            modifier = Modifier,
            rows = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),

            ) {

            items(customCardList) { colors ->
                Box(
                    modifier = Modifier
                        .size(width = 140.dp, height = 20.dp)
                        .border(
                            width = 2.dp,
                            shape = RoundedCornerShape(6.dp),
                            color = colors.borderColor
                        )
                        .background(color = colors.backgroundColor, shape = RoundedCornerShape(6.dp))
                )
                {
                    Text(
                        text = "title",
                        modifier = Modifier.padding(4.dp),
                        color = colors.onBackgroundColor
                    )

                }
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun preveiw() {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Image Classes") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
            )
        },
        bottomBar = {
            BottomNavigationMenu(
                navController = rememberNavController()
            )
        }
    ) { it ->


        HomeScreen(
            modifier = Modifier.padding(it),
            onAllBookSelect = {},
            onNavigateToBook = {}
        )
    }
}