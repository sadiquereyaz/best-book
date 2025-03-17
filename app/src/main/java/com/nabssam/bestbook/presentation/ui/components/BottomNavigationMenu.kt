package com.nabssam.bestbook.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.nabssam.bestbook.R
import com.nabssam.bestbook.presentation.navigation.TopLevelDestination
import com.nabssam.bestbook.presentation.navigation.bottomNavigationLogic


@Composable
fun BottomNavigationMenu(
    navController: NavController
) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination

    val topLevelDestination = TopLevelDestination.fromNavDestination(currentDestination)

    NavigationBar(
        modifier = Modifier,
        containerColor = MaterialTheme.colorScheme.surface
    ) {
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
                selected = isSelected,
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