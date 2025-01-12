package com.nabssam.bestbook.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.nabssam.bestbook.presentation.navigation.AppNavHost
import com.nabssam.bestbook.presentation.ui.components.BBNavSuite

@Composable
fun BestBookApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    BBNavSuite(navController = navController, modifier = Modifier)
    { innerPadding ->
        AppNavHost(navController, modifier, innerPadding)
    }
}
