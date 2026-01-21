package io.alramdhan.lanadi.ui.components

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import io.alramdhan.lanadi.navigation.BottomNavScreen
import io.alramdhan.lanadi.navigation.Screen
import io.alramdhan.lanadi.ui.components.shape.BNCurvedShape

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.BottomCurvedBar(
    bottomNavController: NavController,
    navController: NavController,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    val navigationItems = listOf(
        BottomNavScreen.Home,
        BottomNavScreen.Order,
        BottomNavScreen.History,
        BottomNavScreen.Setting
    )
    val navBackStackEntry by bottomNavController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    // animated fab
    var isFabExpanded by remember { mutableStateOf(false) }
    val fabRotation by animateFloatAsState(
        targetValue = if(isFabExpanded) 135f else 0f,
        animationSpec = tween(durationMillis = 300),
        label = "FabRotation"
    )
    val fabScale by animateFloatAsState(
        targetValue = if(isFabExpanded) 1.1f else 1f,
        animationSpec = tween(durationMillis = 200),
        label = "FabScale"
    )

    Box(
        Modifier.fillMaxWidth(),
        contentAlignment = Alignment.BottomCenter
    ) {
        NavigationBar(
            modifier = Modifier.fillMaxWidth()
                .graphicsLayer {
                    shape = BNCurvedShape(150f)
                    clip = true
                    shadowElevation = 10f
                },
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
            contentColor = Color.White
        ) {
            navigationItems.forEach { screen ->
                NavigationBarItem(
                    selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                    onClick = {
                        bottomNavController.navigate(screen.route) {
                            popUpTo(bottomNavController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = {
                        Icon(screen.icon, contentDescription = screen.title)
                    },
                    label = {
                        Text(screen.title)
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.surface,
                        indicatorColor = MaterialTheme.colorScheme.primary
                    )
                )
            }
        }

        FloatingActionButton(
            onClick = {
                isFabExpanded = !isFabExpanded
                navController.navigate(Screen.QRScanner.route)
            },
            shape = CircleShape,
            containerColor = MaterialTheme.colorScheme.primary, // Warna FAB
            contentColor = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .align(Alignment.TopCenter) // Posisi di tengah atas Box
                .offset(y = (-35).dp) // Mengangkat FAB ke atas agar masuk ke cekungan
                .size(64.dp) // Ukuran FAB
                .scale(fabScale) // Menerapkan animasi scale
                .sharedElement(
                    rememberSharedContentState(key = "btn_login_to_fab"),
                    animatedVisibilityScope = animatedVisibilityScope
                )
        ) {
            Icon(
                imageVector = Icons.Default.QrCodeScanner,
                contentDescription = "Add Item",
                modifier = Modifier
                    .size(32.dp)
                    .rotate(fabRotation) // Menerapkan animasi rotasi
            )
        }
    }
}