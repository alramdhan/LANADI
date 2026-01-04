package io.alramdhan.lanadi.navigation

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import io.alramdhan.lanadi.ui.home.HistoryScreen
import io.alramdhan.lanadi.ui.home.HomeScreen
import io.alramdhan.lanadi.ui.home.OrderScreen
import io.alramdhan.lanadi.ui.home.setting.SettingScreen

@Composable
fun NavigationGraph(
    windowWidthSizeClass: WindowWidthSizeClass?,
    bottomNavController: NavHostController,
    navController: NavController
) {
    NavHost(
        navController = bottomNavController,
        startDestination = BottomNavScreen.Home.route
    ) {
        composable(route = BottomNavScreen.Home.route) {
            HomeScreen(
                widthSizeClass = windowWidthSizeClass,
                navController = navController,
            )
        }
        composable(route = BottomNavScreen.Order.route) {
            OrderScreen(
                widthSizeClass = windowWidthSizeClass,
                navController = navController,
            )
        }
        composable(route = BottomNavScreen.History.route) {
            HistoryScreen(
                widthSizeClass = windowWidthSizeClass,
                navController = navController,
            )
        }
        composable(route = BottomNavScreen.Setting.route) {
            SettingScreen(
                widthSizeClass = windowWidthSizeClass,
                navController = navController,
            )
        }
    }
}