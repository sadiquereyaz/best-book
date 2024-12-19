package com.imageclasses.imageclasses.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.imageclasses.imageclasses.R
import com.imageclasses.imageclasses.navigation.DestinationScreen

enum class BottomNavigationItem(
    val icon: Int,
    val title: String,
    val destinationScreen: DestinationScreen
) {
    HOME(
        icon = R.drawable.ic_launcher_foreground,
        title = "Home",
        destinationScreen = DestinationScreen.home
    ),
    QUIZ(
        icon = R.drawable.ic_launcher_foreground,
        title = "Quiz",
        destinationScreen = DestinationScreen.userProfile
    ),
    USER_RESOURCES(
        icon = R.drawable.ic_launcher_foreground,
        title = "Resources",
        destinationScreen = DestinationScreen.home
    ),
    PROFILE(
        icon = R.drawable.ic_launcher_foreground,
        title = "Profile",
        destinationScreen = DestinationScreen.userProfile
    ),
}

@Composable
fun BottomNavigationMenu(
    selectedItem: BottomNavigationItem,
    navController: NavController
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 4.dp)
            .background(color = Color.White)
    ) {
        for (item in BottomNavigationItem.entries) {
            Image(
                modifier = Modifier
                    .size(40.dp)
                    .padding(4.dp)
                    .weight(1f)
                    .clickable {
                        navController.navigate(item.destinationScreen.route){
                            launchSingleTop = true
                        }
                    },
                painter = painterResource(item.icon),
                contentDescription = item.title,
                colorFilter = if (item == selectedItem) ColorFilter.tint(Color.Black) else ColorFilter.tint(Color.Gray)
            )
        }
    }
}