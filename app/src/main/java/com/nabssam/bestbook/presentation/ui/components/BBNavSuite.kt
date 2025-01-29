package com.nabssam.bestbook.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Settings
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.currentBackStackEntryAsState
import com.nabssam.bestbook.R
import com.nabssam.bestbook.data.repository.auth.AuthManager
import com.nabssam.bestbook.presentation.navigation.Route
import com.nabssam.bestbook.presentation.navigation.TopLevelDestination.Companion.isTopLevel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BBNavSuite(
    navController: NavController,
    modifier: Modifier = Modifier,
    // networkConnectivityObserver: NetworkConnectivityObserver = hiltViewModel(),
    authManager: AuthManager,
    content: @Composable (PaddingValues) -> Unit

) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
//    val drawerState = rememberDrawerState(initialValue = DrawerValue.Open)
    // val snackbarHostState = remember { SnackbarHostState() }


    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            // Side Navigation Panel Content
            ModalDrawerSheet(

            ) {
                NavigationDrawerContent(navController, drawerState, scope, authManager)
            }
        },
        gesturesEnabled = false
    ) {
        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                CenterAlignedTopAppBar(
                    navigationIcon = {
                        if (currentDestination != null && navController.previousBackStackEntry != null && !currentDestination.isTopLevel() /*&& !(currentDestination.hasRoute(
                            Route.SignIn::class
                        ))*/
                        ) {
                            // Up button
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
                                    modifier = Modifier
                                    // .height(100.dp)
                                    //.background(color = Color.Red)
                                    ,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = dimensionResource(R.dimen.topBarTextSize).value.sp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(),
                    /*.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary,
            )*/
                    scrollBehavior = scrollBehavior,
                    actions = {
                        // Add buttons dynamically
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
                /* if (currentDestination?.hasRoute(Route.Home::class) == true)
                     FloatingActionButton(
                         onClick = { navController.navigate(Route.CartRoute()) },
                         containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                         elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
                     ) {
                         Icon(Icons.Filled.ShoppingCart, "shopping")
                     }*/
            },
            snackbarHost = {
                // SnackbarHost(hostState = snackbarHostState)
            }
        ) { innerPadding ->
            content(innerPadding)

            // Observe network state and show Snackbar
            /* NetworkSnackbar(
             networkConnectivityObserver = networkConnectivityObserver,
             snackbarHostState = snackbarHostState
         )*/
        }
    }
}

@Composable
fun NavigationDrawerContent(
    navController: NavController,
    drawerState: DrawerState,
    scope: CoroutineScope,
    authManager: AuthManager
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(Modifier.height(12.dp))
        Text(
            "Drawer Title",
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.titleLarge
        )
        HorizontalDivider()

        Text(
            "Section 1",
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.titleMedium
        )
        NavigationDrawerItem(
            label = { Text("Item 1") },
            selected = false,
            onClick = { /* Handle click */ }
        )
        NavigationDrawerItem(
            label = { Text("Item 2") },
            selected = false,
            onClick = { /* Handle click */ }
        )

        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

        Text(
            "Section 2",
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.titleMedium
        )
        NavigationDrawerItem(
            label = { Text("Settings") },
            selected = false,
            icon = { Icon(Icons.Outlined.Settings, contentDescription = null) },
            badge = { Text("20") }, // Placeholder
            onClick = { /* Handle click */ }
        )
        NavigationDrawerItem(
            label = { Text("Your Order") },
            selected = false,
            onClick = {
                navController.navigate(Route.AllOrderRoute())
                scope.launch { drawerState.close() }
            }
        )

        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

        Text(
            "Section 2",
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.titleMedium
        )
        NavigationDrawerItem(
            label = { Text("Settings") },
            selected = false,
            icon = { Icon(Icons.Outlined.Settings, contentDescription = null) },
            badge = { Text("20") }, // Placeholder
            onClick = { /* Handle click */ }
        )
        NavigationDrawerItem(
            label = { Text("Help and feedback") },
            selected = false,
            icon = { Icon(Icons.Outlined.Info, contentDescription = null) },
            onClick = { /* Handle click */ },
        )

        NavigationDrawerItem(
            label = { Text("Logout") },
            selected = false,
            icon = {Icon(imageVector = ImageVector.vectorResource(R.drawable.log_out), contentDescription = null)},
            onClick = {
                scope.launch {
                    authManager.handleDeviceConflict()
                }
                scope.launch { drawerState.close() }
            }
        )
        Spacer(Modifier.height(12.dp))
    }
}
