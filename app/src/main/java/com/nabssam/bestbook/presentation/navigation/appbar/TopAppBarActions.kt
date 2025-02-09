package com.nabssam.bestbook.presentation.navigation.appbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import com.nabssam.bestbook.R
import com.nabssam.bestbook.presentation.navigation.Route


@Composable
fun TopAppBarActions(
    currentDestination: NavDestination?,
    navController: NavController,
    cartCount: Int
) {
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
            destination.hasRoute(Route.Home::class) ||  destination.hasRoute(Route.AllBookRoute::class)||  destination.hasRoute(Route.BookDetailRoute::class) -> {
                IconButton(onClick = { navController.navigate(Route.CartRoute()) }) {
                    BadgedBox(
                        badge = {
                            if (cartCount > 0) {
                                Badge(
                                    containerColor = Color.Red,
                                    contentColor = Color.White
                                ) {
                                    Text("$cartCount")
                                }
                            }
                        }
                    ) {
                        Icon(
                            Icons.Default.ShoppingCart,
                            contentDescription = "cart"
                        )
                    }
                }
            }

            else -> {

            }
        }
    }
}
