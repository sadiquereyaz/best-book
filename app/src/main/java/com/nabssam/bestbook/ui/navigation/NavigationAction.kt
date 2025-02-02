import androidx.navigation.NavController
import com.nabssam.bestbook.ui.navigation.Route

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

