package io.alramdhan.lanadi.navigation

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import io.alramdhan.lanadi.ui.auth.LoginScreen
import io.alramdhan.lanadi.ui.home.MenuTab

@Composable
fun NavigationStack(windowWidthSizeClass: WindowWidthSizeClass?) {
    val navController = rememberNavController()
    val graph = navController.createGraph(startDestination = Screen.Initial.route) {
        composable(route = Screen.Initial.route) {
            LoginScreen(
                windowWidthSizeClass = windowWidthSizeClass,
                navController = navController
            )
        }
        composable(route = Screen.Login.route) {
            LoginScreen(
                windowWidthSizeClass = windowWidthSizeClass,
                navController = navController
            )
        }
        composable(route = Screen.Main.route) {
            MenuTab(windowWidthSizeClass, navController)
        }
    }

    NavHost(
        navController = navController,
        graph = graph,
//        startDestination = Screen.Initial.route
    )
}