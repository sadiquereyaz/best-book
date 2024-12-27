package com.imageclasses.imageclasses.ui.feature.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.imageclasses.imageclasses.R
import com.imageclasses.imageclasses.ui.components.AutoScrollingImagePager

data class CardColors(
    val backgroundColor: Color,
    val onBackgroundColor: Color,
    val borderColor: Color
)

enum class CardTheme(val colors: CardColors) {
    RED(CardColors(Color(0x6FFFB3B3), Color(0xFF000000), Color(0x31FF8080))),
    GREEN(CardColors(Color(0x6DCCFFCC), Color(0xFF000000), Color(0x3E99FF99))),
    BLUE(CardColors(Color(0x40CCDDFF), Color(0xFF000000), Color(0x3E6699FF))),
    YELLOW(CardColors(Color(0x63FFD58F), Color(0xFF000000), Color(0x54FFFF99))),
    PURPLE(CardColors(Color(0x5CFFCCFF), Color(0xFF000000), Color(0x7EFF99FF)));
}
val customCardList = listOf<CustomCard>(
    CustomCard( CardTheme.RED.colors, quizTitle = "AMU XI"),
    CustomCard(CardTheme.GREEN.colors, quizTitle = "BHU IX"),
    CustomCard(CardTheme.BLUE.colors, quizTitle = "AMU XI-Com"),
    CustomCard(CardTheme.YELLOW.colors, quizTitle = "BHU VI"),
    CustomCard(CardTheme.PURPLE.colors, quizTitle = "JMI XI-Sc"),
    CustomCard(CardTheme.BLUE.colors, quizTitle = "AMU XI-Dip"), CustomCard( CardTheme.RED.colors, quizTitle = "AMU XI"),
    CustomCard(CardTheme.GREEN.colors, quizTitle = "BHU IX"),
    CustomCard(CardTheme.BLUE.colors, quizTitle = "AMU XI-Com"),
    CustomCard(CardTheme.YELLOW.colors, quizTitle = "BHU VI"),
    CustomCard(CardTheme.PURPLE.colors, quizTitle = "JMI XI-Sc"),
    CustomCard(CardTheme.BLUE.colors, quizTitle = "AMU XI-Dip"),

)
data class CustomCard(
    val colors: CardColors,
    val quizTitle: String = "Exam Name"
)


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier.fillMaxSize(),
    onAllBookSelect: (String) -> Unit,
    onNavigateToBook: (Int) -> Unit,
    onBannerClick: () -> Unit,
    navigateToQuizCategory: (String) -> Unit,
    onAllQuizSelect: (String) -> Unit

) {
    // Display 4 items
    val pagerState = rememberPagerState(pageCount = { customCardList.size })

    Column(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
    ) {
        AutoScrollingImagePager(modifier = Modifier)
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Popular Books", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.weight(1f))
            TextButton(
                onClick = { onAllBookSelect("DUMMY_EXAM_ID") }
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
                            .clickable { onNavigateToBook(0) }
                            .border(
                                width = 0.5.dp,
                                shape = RoundedCornerShape(6.dp),
                                color = Color.Black
                            )
                          //  .clip(shape = RoundedCornerShape(6.dp))
                            //.padding(6.dp)
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
                                width = 0.5.dp,
                                shape = RoundedCornerShape(6.dp),
                                color = Color.Black
                            )
                            //.padding(6.dp)
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
                onClick = {onAllQuizSelect("DUMMY_ID")}
            ) {
                Text(text = "View all quiz", style = MaterialTheme.typography.labelLarge)
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        
        //  quiz tile
        LazyHorizontalGrid(
            modifier = Modifier.height(100.dp),
            rows = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
            items(customCardList) { card ->
                QuizCard(card)
            }

        }
    }
}
@Composable
fun QuizCard(card: CustomCard) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .border(
                width = 2.dp,
                shape = RoundedCornerShape(6.dp),
                color = card.colors.borderColor
            )
            .background(color = card.colors.backgroundColor, shape = RoundedCornerShape(6.dp))
            .aspectRatio(2f)
    ) {
        Text(
            text = card.quizTitle,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(4.dp),
            color = card.colors.onBackgroundColor
        )
    }
}

