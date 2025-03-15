package com.nabssam.bestbook.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.nabssam.bestbook.R
import com.nabssam.bestbook.data.repository.auth.AuthManager
import com.nabssam.bestbook.presentation.navigation.TopLevelDestination.Companion.isTopLevel
import com.nabssam.bestbook.presentation.navigation.appbar.TopAppBarActions
import com.nabssam.bestbook.presentation.ui.snackbar.KeyboardAwareSnackbarHost
import com.nabssam.bestbook.presentation.ui.snackbar.SnackbarManager
import com.nabssam.bestbook.presentation.ui.snackbar.SnackbarObserver
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BBNavSuite(
    navController: NavController,
    modifier: Modifier = Modifier,
    // networkConnectivityObserver: NetworkConnectivityObserver = hiltViewModel(),
    authManager: AuthManager,
    cartItemCount: Int,
    snackbarManager: SnackbarManager,
    content: @Composable (PaddingValues) -> Unit,

    ) {
   // val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
//    val drawerState = rememberDrawerState(initialValue = DrawerValue.Open)
    val snackbarHostState = remember { SnackbarHostState() }
    ///val cartCount:Int? by cartItemCount.getCartItemCount.collectAsState()
//    val cartCount by appViewModel.cartItemCount.collectAsState()
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
            modifier = Modifier
                //.nestedScroll(scrollBehavior.nestedScrollConnection)
            ,
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
//                        if (currentDestination != null && currentDestination.isTopLevel()) {
//                            ImageClassesTitle()
//                        } else {
                            val title = navBackStackEntry?.arguments?.getString("title")
                            Box(Modifier, contentAlignment = Alignment.Center) {
                                Text(
                                    text = title ?: "Best Book",
                                    style = androidx.compose.ui.text.TextStyle(
                                        brush = gradientBrush()
                                    ),

                                    modifier = Modifier.background(
                                        color = Color.Transparent
                                    )
                                        .padding(horizontal = 2.dp),

                                    fontWeight = FontWeight.Bold,
                                    fontSize = dimensionResource(R.dimen.topBarTextSize).value.sp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
//                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(),
                    //scrollBehavior = scrollBehavior,
                    actions = {
                        TopAppBarActions(currentDestination, navController, cartItemCount?: 0)
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
                 //SnackbarHost(hostState = snackbarHostState)
            }
        ) { innerPadding ->
            content(innerPadding)
            KeyboardAwareSnackbarHost(
                modifier = Modifier.padding(innerPadding /*bottom = 32.dp*/ ),
                hostState = snackbarHostState
            )
            SnackbarObserver(snackbarManager = snackbarManager, snackbarHostState = snackbarHostState)
        }
    }
}

