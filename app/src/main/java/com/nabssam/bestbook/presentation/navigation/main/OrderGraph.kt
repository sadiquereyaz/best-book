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
import com.nabssam.bestbook.presentation.ui.address.AddressScreen
import com.nabssam.bestbook.presentation.ui.address.ViewModelAddress
import com.nabssam.bestbook.presentation.ui.order.all.OrderListScreen
import com.nabssam.bestbook.presentation.ui.order.all.SummaryScreen
import com.nabssam.bestbook.presentation.ui.order.detail.OrderDetailsScreen
import com.nabssam.bestbook.presentation.ui.order.detail.VMOrderDetail
import com.nabssam.bestbook.utils.DummyData

fun NavGraphBuilder.orderGraph(navController: NavHostController) {
    navigation<Route.OrderGraph>(
        startDestination =
        Route.AddressRoute()
//        Route.OrderDetailRoute(orderId = "heehehe", title = "Product Name")
//        Route.AllOrderRoute(title = "All Order")
    ) {
        composable<Route.AddressRoute> { backStackEntry ->
            //val routeObj: Route.Cart = backStackEntry.toRoute()
            val viewModel: ViewModelAddress = hiltViewModel()
            val state by viewModel.uiState.collectAsState()
            AddressScreen(
                uiState = state,
                onEvent = { viewModel.onEvent(it) },
                navigateToSummary = {
                    navController.navigate(Route.OrderSummaryRoute(deliveryCharge = it))
                }
            )
        }

        composable<Route.OrderSummaryRoute> {
//            val viewModel: ViewModelAddress = hiltViewModel()
//            val state by viewModel.uiState.collectAsState()
            SummaryScreen(
               /* uiState = state,
                onEvent = { viewModel.onEvent(it) },
                navigateToSummary = {
                    navController.navigate(Route.AllOrderRoute())
                }*/
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
            OrderListScreen(
                orders = DummyData.orders,
                onOrderClick = {
                    navController.navigate(Route.OrderDetailRoute(orderId = it, title = "Order Details"))
                }
            )
        }


        dialog<Route.PaymentDialogRoute> {
            // PaymentStatusDialog()
        }
    }
}