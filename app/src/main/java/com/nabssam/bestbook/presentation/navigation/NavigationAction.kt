import androidx.navigation.NavController
import com.nabssam.bestbook.presentation.navigation.Route

fun NavController.bottomNavigationLogic(
    route: Route
) {
    navigate(route = route) {
        popUpTo(Route.Home) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}

