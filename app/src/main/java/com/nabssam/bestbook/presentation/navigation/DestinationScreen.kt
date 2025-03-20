package com.nabssam.bestbook.presentation.navigation

import androidx.annotation.DrawableRes
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import com.nabssam.bestbook.R
import kotlinx.serialization.Serializable

sealed interface Route {

    @Serializable
    data object AuthGraph : Route
    @Serializable
    data object OrderGraph : Route
    @Serializable
    data object MainGraph : Route
    @Serializable
    data object QuizGraph : Route

    @Serializable
    data class SignIn(val title: String = "Best Book") : Route

    @Serializable
    data class SignUp(val title: String = "Sign Up") : Route


    @Serializable
    data object Home : Route

    @Serializable
    data object Notification : Route

    @Serializable
    data object Ebook : Route

    @Serializable
    data object Profile : Route

    @Serializable
    data class QuizSubjectRoute(val title: String = "Category") : Route
    @Serializable
    data class MCQRoute(val title: String = "Question") : Route

    @Serializable
    data class AllBookRoute(val title: String = "Book Store", val targetExam: String? = null) : Route

    @Serializable
    data class BookDetailRoute(val id: String, val title: String = "Book Detail") : Route
    @Serializable
    data class Contest(val id: String, val title: String = "Book Detail") : Route

    @Serializable
    data class AllReviewRoute(val title: String= "All Reviews") : Route

    @Serializable
    data class CartRoute(val title: String = "Your Cart") : Route

    @Serializable
    data class AddressRoute(val title: String = "Delivery Address") : Route

    @Serializable
    data class AllOrderRoute(val title: String = "All Orders") : Route

    @Serializable
    data class OrderSummaryRoute(val title: String = "Order Summary", val deliveryCharge: Int = 0) : Route

    @Serializable
    data class OrderDetailRoute(val orderId: String, val title: String = "Order Detail") : Route

    @Serializable
    data object PaymentDialogRoute : Route

    @Serializable
    data class EbookPreviewRoute(val title: String = "Ebook Preview") : Route
}

enum class TopLevelDestination(
    val label: String,
    val route: Route,
    @DrawableRes val selectedIcon: Int,
    @DrawableRes val unSelectedIcon: Int
) {

    HOME(
        selectedIcon = R.drawable.home_filled,
        unSelectedIcon =R.drawable.home_outlined,
        label = "Home",
        route = Route.Home
    ),
    EBOOK(
        selectedIcon = R.drawable.ebook_filled,
        unSelectedIcon =R.drawable.ebook_oulined,
        label = "E-book",
        route = Route.Ebook
    ),
    NOTIFICATION(
        selectedIcon = R.drawable.notification_filled,
        unSelectedIcon =R.drawable.notificaiton_outlined,
        label = "Notification",
        route = Route.Notification
    ),
    PROFILE(
        selectedIcon = R.drawable.profile_f,
        unSelectedIcon =R.drawable.profile_o,
        label = "Profile",
        route = Route.Profile
    ),
    ;

    companion object {
        val START_DESTINATION = HOME

        fun fromNavDestination(destination: NavDestination?): TopLevelDestination {
            return entries.find { dest ->
                destination?.hierarchy?.any {
                    it.hasRoute(dest.route::class)
                } == true
            } ?: START_DESTINATION
        }

        fun NavDestination.isTopLevel(): Boolean {
            return entries.any {
                hasRoute(it.route::class)
            }
        }
    }
}