package com.nabssam.bestbook.presentation.ui.components

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.nabssam.bestbook.data.repository.auth.AuthManager
import com.nabssam.bestbook.presentation.navigation.Route
import com.nabssam.bestbook.presentation.navigation.TopLevelDestination.Companion.isTopLevel
import com.nabssam.bestbook.presentation.ui.snackbar.KeyboardAwareSnackbarHost
import com.nabssam.bestbook.presentation.ui.snackbar.SnackbarManager
import com.nabssam.bestbook.presentation.ui.snackbar.SnackbarObserver
private const val TAG = "BB_NAV_SUITE"
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
            modifier = Modifier,
            topBar = {
                BBTopAppBar(
                    currentDestination,
                    navController,
                    cartItemCount,
                    scope,
                    drawerState
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
                modifier = Modifier.padding(innerPadding /*bottom = 32.dp*/),
                hostState = snackbarHostState
            )

            SnackbarObserver(
                snackbarManager = snackbarManager,
                snackbarHostState = snackbarHostState
            )
        }
    }
}

