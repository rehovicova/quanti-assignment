package cz.quanti.spacexrockets.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import cz.quanti.spacexrockets.ui.screens.detail.DetailScreen
import cz.quanti.spacexrockets.ui.screens.launch.LaunchScreen
import cz.quanti.spacexrockets.ui.screens.rockets.RocketsScreen


@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Rockets.route) {

        composable(route = Screen.Rockets.route) {
            RocketsScreen(
                onItemClick = { itemId ->
                    navController.navigate(Screen.Detail.createRoute(itemId))
                }
            )
        }

        composable(
            route = Screen.Detail.route,
            arguments = listOf(navArgument("rocketId") { type = NavType.StringType })
        ) { backStackEntry ->
            val rocketId = backStackEntry.arguments?.getString("rocketId")
            if (rocketId != null) {
                DetailScreen(
                    id = rocketId,
                    navigateBack = { navController.popBackStack() },
                    navigateToLaunch = { navController.navigate(Screen.Launch.route) }
                )
            }
        }

        composable(route = Screen.Launch.route) {
            LaunchScreen(
                navigateBack = { navController.popBackStack() }
            )
        }
    }
}

sealed class Screen(val route: String) {
    object Rockets : Screen("rockets")
    object Detail : Screen("detail/{rocketId}") {
        fun createRoute(rocketId: String) = "detail/$rocketId"
    }

    object Launch : Screen("launch")
}