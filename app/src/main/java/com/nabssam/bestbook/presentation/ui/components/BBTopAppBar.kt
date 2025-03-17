package com.nabssam.bestbook.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.nabssam.bestbook.R
import com.nabssam.bestbook.presentation.navigation.TopLevelDestination.Companion.isTopLevel
import com.nabssam.bestbook.presentation.navigation.appbar.TopAppBarActions
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BBTopAppBar(
    currentDestination: androidx.navigation.NavDestination?,
    navController: NavController,
    cartItemCount: Int,
    scope: kotlinx.coroutines.CoroutineScope,
    drawerState: androidx.compose.material3.DrawerState
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    CenterAlignedTopAppBar(
        navigationIcon = {
            if (currentDestination != null && navController.previousBackStackEntry != null && !currentDestination.isTopLevel()) {
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
                    style = TextStyle(
                        brush = gradientBrush()
                    ),

                    modifier = Modifier
                        .background(
                            color = Color.Transparent
                        )
                        .padding(horizontal = 2.dp),

                    fontWeight = FontWeight.Bold,
                    fontSize = dimensionResource(R.dimen.topBarTextSize).value.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        },
        /*colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = */
        /*MaterialTheme.colorScheme.surface*/
        /*colorResource(R.color.navigation_container)
                ),*/
        actions = {
            TopAppBarActions(currentDestination, navController, cartItemCount ?: 0)
        },
    )
}