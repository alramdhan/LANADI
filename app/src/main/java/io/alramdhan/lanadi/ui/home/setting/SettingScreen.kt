package io.alramdhan.lanadi.ui.home.setting

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import io.alramdhan.lanadi.navigation.Screen

@Composable
fun SettingScreen(
    widthSizeClass: WindowWidthSizeClass?,
    navController: NavController
) {
    LazyColumn (Modifier.fillMaxSize()) {
        item {
            ListItem(
                modifier = Modifier.fillMaxWidth()
                    .clickable(
                        interactionSource = remember(Unit) { MutableInteractionSource() },
                        indication = LocalIndication.current,
                        onClick = {
                            navController.navigate(Screen.Login.route) {
                                popUpTo(Screen.Main.route) {
                                    inclusive = true
                                }
                            }
                        }
                    ),
                headlineContent = { Text("Keluar") },
                trailingContent = { Icon(Icons.AutoMirrored.Filled.Logout, contentDescription = "icon keluar") }
            )
        }
    }
}