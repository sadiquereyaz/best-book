package com.imageclasses.imageclasses.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.imageclasses.imageclasses.ui.components.BottomNavigationMenu
import com.imageclasses.imageclasses.ui.feature.cart.CartScreen
import com.imageclasses.imageclasses.ui.feature.quiz.AllQuizListRoute
/*


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun Preview(
    navController: NavController = rememberNavController()

) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Image Classes") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
            )
        },
        bottomBar = {
            BottomNavigationMenu(
                navController = rememberNavController()
            )
        }
    ) { it ->
        CartScreen(
            modifier = Modifier.padding(it),
        )
    }
}

*/