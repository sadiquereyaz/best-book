/*
package com.nabssam.bestbook.presentation.ui.components

import androidx.compose.material.icons.filled.Menu

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.currentBackStackEntryAsState
import com.nabssam.bestbook.R
import com.nabssam.bestbook.presentation.navigation.Route
import com.nabssam.bestbook.presentation.navigation.TopLevelDestination.Companion.isTopLevel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BBNavSuite1(
    navController: NavController,
    modifier: Modifier = Modifier,
    content: @Composable (PaddingValues) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            // Side Navigation Panel Content
            NavigationDrawerContent(navController, drawerState)
        }
    ) {
        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                CenterAlignedTopAppBar(
                    navigationIcon = {
                        if (currentDestination != null && navController.previousBackStackEntry != null && !currentDestination.isTopLevel()) {
                            IconButton(onClick = { navController.popBackStack() }) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Back"
                                )
                            }
                        } else if (currentDestination != null && currentDestination.isTopLevel()) {
                            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                Icon(
                                    imageVector = Icons.Default.Menu,
                                    contentDescription = "Menu"
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
                                    modifier = Modifier,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = dimensionResource(R.dimen.topBarTextSize).value.sp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(),
                    scrollBehavior = scrollBehavior,
                    actions = {
                        currentDestination?.let { destination ->
                            when {
                                destination.hasRoute(Route.Ebook::class) -> {
                                    IconButton(onClick = { }) {
                                        Icon(
                                            imageVector = ImageVector.vectorResource(R.drawable.ebook),
                                            contentDescription = "ebook_store"
                                        )
                                    }
                                }
                                destination.hasRoute(Route.AuthGraph::class) -> {
                                    IconButton(onClick = { }) {
                                        Icon(
                                            imageVector = ImageVector.vectorResource(R.drawable.ebook),
                                            contentDescription = "ebook_store"
                                        )
                                    }
                                }
                                else -> {
                                    IconButton(onClick = { navController.navigate(Route.CartRoute()) }) {
                                        Icon(
                                            Icons.Default.ShoppingCart,
                                            contentDescription = "cart"
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
                if (currentDestination?.hasRoute(Route.Home::class) == true)
                    FloatingActionButton(
                        onClick = { navController.navigate(Route.CartRoute()) },
                        containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                        elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
                    ) {
                        Icon(Icons.Filled.ShoppingCart, "shopping")
                    }
            },
            snackbarHost = {
                // SnackbarHost(hostState = snackbarHostState)
            }
        ) { innerPadding ->
            content(innerPadding)
        }
    }
}

@Composable
fun NavigationDrawerContent1(
    navController: NavController,
    drawerState: DrawerState
) {
    val scope = rememberCoroutineScope()

    // Add your side navigation panel content here
    Text("Side Navigation Panel")
    // Example: Add navigation items
    NavigationDrawerItem(
        label = { Text("Home") },
        selected = false,
        onClick = {
            navController.navigate(Route.Home::class)
            scope.launch { drawerState.close() }
        }
    )
    NavigationDrawerItem(
        label = { Text("Ebooks") },
        selected = false,
        onClick = {
            navController.navigate(Route.Ebook::class)
            scope.launch { drawerState.close() }
        }
    )
}*/
