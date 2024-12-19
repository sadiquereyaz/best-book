package com.imageclasses.imageclasses.ui

import android.graphics.drawable.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import com.imageclasses.imageclasses.R
import com.imageclasses.imageclasses.navigation.DestinationScreen

enum class BottomNavigationItem(
    val selectedIcon: ImageVector,
    val unSelectedIcon: ImageVector,
    val title: String,
    val destinationScreen: DestinationScreen
) {
    HOME(
        selectedIcon = Icons.Filled.Home,
        unSelectedIcon = Icons.Outlined.Home,
        title = "Home",
        destinationScreen = DestinationScreen.home
    ),
    QUIZ(
        selectedIcon = Icons.Filled.DateRange,
        unSelectedIcon = Icons.Outlined.DateRange,
        title = "Quiz",
        destinationScreen = DestinationScreen.userProfile
    ),
    USER_RESOURCES(
        selectedIcon = Icons.Filled.Edit,
        unSelectedIcon = Icons.Outlined.Edit,
        title = "Resources",
        destinationScreen = DestinationScreen.home
    ),
    PROFILE(
        selectedIcon = Icons.Filled.Person,
        unSelectedIcon = Icons.Outlined.Person,
        title = "Profile",
        destinationScreen = DestinationScreen.userProfile
    ),
}

@Composable
fun BottomNavigationMenu(
    navController: NavController
) {
    var selectedItem by remember { mutableStateOf(BottomNavigationItem.HOME) }
    val items = listOf("Home", "Quiz", "Playlists")
    val selectedIcons = listOf(Icons.Filled.Home, Icons.Filled.Favorite, Icons.Filled.Star)
    val unselectedIcons =
        listOf(Icons.Outlined.Home, Icons.Outlined.FavoriteBorder, Icons.Outlined.Home)

    NavigationBar {
        for (item in BottomNavigationItem.entries) {
            NavigationBarItem(
                icon = {
                    Icon(
                        if (item == selectedItem) item.selectedIcon else item.unSelectedIcon,
                        contentDescription = item.title
                    )
                },
                label = {
                    Text(item.title)
                },
                selected = selectedItem == item,
                onClick = { selectedItem = item }

            )
        }
    }
}

