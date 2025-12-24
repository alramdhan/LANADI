package io.alramdhan.lanadi.navigation

sealed class Screen(val route: String) {
    data object Initial: Screen("initial_screen")
    data object Login: Screen("login_screen")
}