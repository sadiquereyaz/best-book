package com.nabssam.bestbook.presentation.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.nabssam.bestbook.presentation.navigation.TopLevelDestination
import com.nabssam.bestbook.presentation.navigation.bottomNavigationLogic


@Composable
fun BottomNavigationMenu(
    navController: NavController
) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination

    val topLevelDestination = TopLevelDestination.fromNavDestination(currentDestination)

    NavigationBar(tonalElevation = 12.dp) {
        TopLevelDestination.entries.forEach { topLevelRoute ->
            val isSelected = topLevelRoute == topLevelDestination
            NavigationBarItem(
                icon = {
                    Icon(
                        if (isSelected)
                            topLevelRoute.selectedIcon else topLevelRoute.unSelectedIcon,
                        contentDescription = topLevelRoute.label
                    )
                },
                label = {
                    Text(topLevelRoute.label)
                },
                selected = isSelected ,
                onClick = {
                    if (!isSelected) {
                        navController.bottomNavigationLogic(topLevelRoute.route)
                    }
                    //selectedItem = item.destinationScreen.route
                }
            )
        }
    }
}