package io.alramdhan.lanadi.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun NavigationGraph(
    bottomNavController: NavHostController,
    listScreen: List<ScreenTabItem>
) {
    NavHost(
        navController = bottomNavController,
        startDestination = BottomNavScreen.Home.route
    ) {
        listScreen.forEach { item ->
            composable(route = item.route) {
                item.content()
            }
        }
    }
}