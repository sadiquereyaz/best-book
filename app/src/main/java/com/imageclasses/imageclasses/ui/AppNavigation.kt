package com.imageclasses.imageclasses.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.Text

import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.imageclasses.imageclasses.ui.navigation.DestinationScreen
import com.imageclasses.imageclasses.ui.components.ImageClassesTitle
import com.imageclasses.imageclasses.ui.navigation.AppNavHost

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController: NavHostController = rememberNavController()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    if (true) ImageClassesTitle()
                    else Text(
                        text = "Heading",
                        modifier = Modifier,
                        fontWeight = FontWeight.Bold,
//                        fontSize = 28.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                scrollBehavior = scrollBehavior
            )
        },
        bottomBar = {
            BottomNavigationMenu(
                navController = navController
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* do something */ },
                containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
            ) {
                Icon(Icons.Filled.ShoppingCart, "shopping")
            }
        }
    ) { innerPadding ->
        val isLoggedIn = isLoggedIn()
        LaunchedEffect(isLoggedIn) {
            if (!isLoggedIn) {
                navController.navigate(DestinationScreen.auth.route) {
                    popUpTo(DestinationScreen.auth.route) { inclusive = true }
                }
            }
        }
        AppNavHost(navController, isLoggedIn, modifier, innerPadding)
    }
}

fun isLoggedIn(): Boolean {
    return true
}
