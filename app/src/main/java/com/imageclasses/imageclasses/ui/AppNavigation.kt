package com.imageclasses.imageclasses.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.imageclasses.imageclasses.auth.FirebaseAuth
import com.imageclasses.imageclasses.ui.components.ImageClassesTitle
import com.imageclasses.imageclasses.ui.feature.account.auth.SignUpRoute
import com.imageclasses.imageclasses.ui.feature.bookList.ProfileRoute
import com.imageclasses.imageclasses.ui.navigation.AppNavHost
import com.imageclasses.imageclasses.ui.navigation.auth.AuthGraphRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    //val currentDestination = navController.currentBackStackEntryAsState().value?.destination?.route
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            /*if (*//*currentDestination == ProfileRoute("DSF_DSS").toString()*//*true) {
                CenterAlignedTopAppBar(
                    title = {
                        if (false) ImageClassesTitle()
                        else {
                            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                Text(
                                    text = ProfileRoute("dsf").toString() *//*+ currentDestination*//*,
                                    *//*modifier = Modifier
                                       // .height(100.dp)
                                       .background(color = Color.Red),*//*
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 28.sp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                    scrollBehavior = scrollBehavior
                )
            }*/
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
        /*LaunchedEffect(isLoggedIn) {
            if (!isLoggedIn) {
               *//* navController.navigate(AuthGraphRoute) {
                    popUpTo(SignUpRoute("Signup")) { inclusive = true }
                }*//*
            }
        }*/
        AppNavHost(navController, isLoggedIn, modifier, innerPadding)
    }
}

fun isLoggedIn(): Boolean {
    val firebaseAuth = FirebaseAuth()
    return firebaseAuth.isUserAlreadyLoggedIn()
    //  return false
}
