package io.alramdhan.lanadi.navigation

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.alramdhan.lanadi.ui.auth.LoginScreen

@Composable
fun NavigationStack(windowWidthSizeClass: WindowWidthSizeClass?) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Initial.route) {
        composable(route = Screen.Login.route) {
            LoginScreen(
                windowWidthSizeClass = windowWidthSizeClass,
                navController = navController
            )
        }
    }
}