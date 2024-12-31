package com.nabssam.bestbook.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.nabssam.bestbook.ui.components.IcNavSuite
import com.nabssam.bestbook.ui.navigation.AppNavHost

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    IcNavSuite(navController = navController,modifier = Modifier)
    { innerPadding ->
        AppNavHost(navController, modifier, innerPadding)
    }
}
