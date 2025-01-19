package com.nabssam.bestbook.presentation.navigation.main

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.navigation
import com.nabssam.bestbook.presentation.navigation.Route
import com.nabssam.bestbook.presentation.ui.address.AddAddressScreen
import com.nabssam.bestbook.presentation.ui.order.detail.OrderDetailsScreen
import com.nabssam.bestbook.presentation.ui.order.detail.VMOrderDetail

fun NavGraphBuilder.orderGraph(navController: NavHostController) {
    navigation<Route.OrderGraph>(
        startDestination =
//        Route.AddressRoute()
        Route.OrderDetailRoute(orderId = "heehehe", title = "Product Name")
    ) {
        composable<Route.AddAddressRoute> { backStackEntry ->
            //val routeObj: Route.Cart = backStackEntry.toRoute()
            AddAddressScreen(
                goToPayment = {
                    navController.navigate(Route.AllOrderRoute())
                }
            )
        }

        composable<Route.OrderDetailRoute> { backStackEntry ->
            val vm = hiltViewModel<VMOrderDetail>()
            val state by vm.uiState.collectAsState()
            OrderDetailsScreen(
                state = state,
                navigateToProduct = {it->
                    navController.navigate(Route.BookDetailRoute(id = it))
                },
                onEvent = { vm.onEvent(it) }
            )
        }

        composable<Route.AllOrderRoute> { backStackEntry ->
            //val routeObj: Route.Cart = backStackEntry.toRoute()

        }


        dialog<Route.PaymentDialogRoute> {
            // PaymentStatusDialog()
        }
    }
}