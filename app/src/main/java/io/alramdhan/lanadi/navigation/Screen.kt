package io.alramdhan.lanadi.navigation

sealed class Screen(val route: String) {
    data object Initial: Screen("initial_screen")
    data object Login: Screen("login_screen")
    data object Main: Screen("main_screen")
    data object QRScanner: Screen("qr_scanner_screen")

    data object Cart: Screen("cart_screen")
}