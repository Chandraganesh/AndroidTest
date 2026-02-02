package lu.post.eval.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import lu.post.eval.domain.model.Movie
import lu.post.eval.ui.screens.home.HomeScreen

/**
 * Sealed class defining all navigation destinations in the app.
 * Each screen has a unique route string for navigation.
 */
sealed class Screen(val route: String) {
    /** Home screen with movie search functionality */
    data object Home : Screen("home")
    data object MovieDetail : Screen("MovieDetail")
}

/**
 * Main navigation host for the application.
 * Defines the navigation graph and handles screen transitions.
 *
 * @param modifier Optional modifier for the NavHost
 * @param navController The navigation controller (created by default)
 * @param startDestination The initial screen route
 */
@Composable
fun EvalNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.Home.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(Screen.Home.route) {
            HomeScreen()
        }
    }
}
