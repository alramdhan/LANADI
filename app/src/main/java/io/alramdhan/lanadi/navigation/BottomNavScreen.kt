package io.alramdhan.lanadi.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.NoteAlt
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

data class ScreenTabItem(
    val route: String,
    val content: @Composable () -> Unit
)

sealed class BottomNavScreen(
    val title: String,
    val icon: ImageVector,
    val route: String
) {
    data object Home: BottomNavScreen("Home", Icons.Outlined.Home, "home_screen")
    data object Order: BottomNavScreen("Order", Icons.Outlined.NoteAlt, "order_screen")
    data object History: BottomNavScreen("History", Icons.Outlined.History, "history_screen")
    data object Setting: BottomNavScreen("Setting", Icons.Outlined.Settings, "setting_screen")
}
