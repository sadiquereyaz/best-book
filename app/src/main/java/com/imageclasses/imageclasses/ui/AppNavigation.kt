package com.imageclasses.imageclasses.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.imageclasses.imageclasses.ui.components.IcNavSuite
import com.imageclasses.imageclasses.ui.navigation.AppNavHost

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
