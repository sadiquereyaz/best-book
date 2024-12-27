package com.imageclasses.imageclasses.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.currentBackStackEntryAsState
import com.imageclasses.imageclasses.R
import com.imageclasses.imageclasses.ui.navigation.Route
import com.imageclasses.imageclasses.ui.navigation.TopLevelDestination.Companion.isTopLevel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IcNavSuite(
    navController: NavController,
    modifier: Modifier = Modifier,
    content: @Composable (PaddingValues) -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    if (currentDestination != null && !currentDestination.isTopLevel() && !(currentDestination.hasRoute(
                            Route.AuthGraph::class
                        ))
                    ) {
                        // Up button
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }
                },
                title = {
                    if (currentDestination != null && currentDestination.isTopLevel()) {
                        ImageClassesTitle()
                    } else {
                        val title = navBackStackEntry?.arguments?.getString("title")
                        Box(Modifier, contentAlignment = Alignment.Center) {
                            Text(
                                text = title.toString(),
                                modifier = Modifier
                                // .height(100.dp)
                                //.background(color = Color.Red)
                                ,
                                fontWeight = FontWeight.Bold,
                                fontSize = 28.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors()
                    /*.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                )*/,
                scrollBehavior = scrollBehavior,
                actions = {
                    // Add buttons dynamically
                    currentDestination?.let { destination ->
                        when {
                            destination.hasRoute(Route.Ebook::class) -> {
                                IconButton(onClick = { /*Navigate to Cart*/TODO() }) {
                                    Icon(
                                        imageVector = ImageVector.vectorResource(R.drawable.ebook),
                                        contentDescription = "ebook_store"
                                    )
                                }
                            }
                        }
                    }
                },
            )
        },
        bottomBar = {
            if (currentDestination != null && currentDestination.isTopLevel())
                BottomNavigationMenu(navController = navController)
        },
        floatingActionButton = {
            if (currentDestination?.hasRoute(Route.AllBook::class) == true)
                FloatingActionButton(
                    onClick = { /* do something */ },
                    containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                    elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
                ) {
                    Icon(Icons.Filled.ShoppingCart, "shopping")
                }
        }) {innerPadding->
        content(innerPadding)
    }
}