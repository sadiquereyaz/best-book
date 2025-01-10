package com.nabssam.bestbook.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.nabssam.bestbook.presentation.ui.components.BBNavSuite
import com.nabssam.bestbook.presentation.ui.cart.claude.CartScreenClaude

@Composable
fun BestBookApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    BBNavSuite(navController = navController, modifier = Modifier)
    { innerPadding ->
        //AppNavHost(navController, modifier, innerPadding)
        CartScreenClaude(viewModel = hiltViewModel(), userId = "TODO()", onNavigateToProduct = {})
    }
}
