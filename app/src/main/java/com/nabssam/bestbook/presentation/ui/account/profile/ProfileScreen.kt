package com.nabssam.bestbook.presentation.ui.account.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.nabssam.bestbook.R
import com.nabssam.bestbook.domain.model.User
import com.nabssam.bestbook.presentation.ui.animation.MyLottieAnimation


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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen() {
    val user = User(
        id = "1",
        picUrl = "https://example.com/profile1.jpg",
        username = "JohnDoe",
        email = "john.doe@example.com",
        isAdmin = false,
        phone = "9876543210",
        currentClass = "10th",
        schoolName = "Springfield High",
        targetExams = listOf("JEE", "NEET"),
        subscribedEbooks = listOf("Maths Basics", "Physics Guide")
    )
    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = {
//                    Text(
//                        text = "PORT",
//                        style = MaterialTheme.typography.titleLarge
//                    )
//                },
//                colors = TopAppBarDefaults.topAppBarColors(
//                    containerColor = Color.White
//                )
//            )
//        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
            // Profile Header

            ProfileHeader(user)
            Divider(
                color = Color.LightGray.copy(alpha = 0.8f),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, bottom = 12.dp)
            )
            // Menu Items

            ProfileMenuItem(text = "Purchased Books", icon = R.drawable.ebook)
            ProfileMenuItem(text = "My Purchases", icon = R.drawable.pyq)
            ProfileMenuItem(text = "My Exams", icon = R.drawable.profile_placeholder)

            // My Learning Activity
            Spacer(modifier = Modifier.height(16.dp))
            SectionTitle(text = "My Learning Activity", color = Blue)

            // Daily Goal Card
            DailyGoalCard()

            // Weekly Activity
            Spacer(modifier = Modifier.height(16.dp))
            SectionTitle(text = "My Weekly Activity", color = Blue)
            WeeklyActivityCard()
            Spacer(modifier = Modifier.height(16.dp))
//            progress
            SectionTitle(text = "My Progress", color = Transparent)
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 2.dp
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ProgressCard(
                        0.98f,
                        1,
                        radius = 40.dp,
                        text = "Attempted Quiz",
                        fontSize = 20.sp

                    )

                    ProgressCard(
                        0.45f,
                        2,
                        radius = 40.dp,
                        color = Color.Magenta,
                        text = "Completed Quiz",
                        fontSize = 20.sp

                    )
                }
            }

        }
    }
}

@Composable
fun ProfileHeader(user: User) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Profile Image
        Image(
            painter = painterResource(id = R.drawable.profile_placeholder),
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(Color.Gray)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(
                text = user.username,
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 20.sp

            )
            Text(
                text = user.currentClass + ", " + user.targetExams,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
            Text(
                text = "Target Year: 2024",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun ProfileMenuItem(text: String, icon: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()

            .padding(vertical = 8.dp, horizontal = 4.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .padding(vertical = 12.dp, horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(icon),
                    contentDescription = "book icon",
                    modifier = Modifier.width(28.dp),

                    )
                Text(
                    modifier = Modifier.padding(4.dp),
                    text = text,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            MyLottieAnimation(
                modifier = Modifier
                    .width(40.dp)
                    .height(40.dp),
                LottieCompositionSpec.RawRes(R.raw.animated_right_arrow)
            )
        }
    }
}

@Composable
fun SectionTitle(text: String, color: Color) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Icon(
            imageVector = Icons.Default.Edit,
            contentDescription = "Edit",
            tint = color
        )
    }
}

@Composable
fun DailyGoalCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "My Daily Goal",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "0/10 Qs",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun WeeklyActivityCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Showing for 24-30 Mar",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ActivityItem(value = "0", label = "Questions Solved")
                ActivityItem(value = "0", label = "Correct Questions")
                ActivityItem(value = "0", label = "Challenge Taken")
            }
        }
    }
}

@Composable
fun ActivityItem(value: String, label: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )
    }
}

@Composable
fun CustomColorBlobWithImage(modifier: Modifier = Modifier) {
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