package io.alramdhan.lanadi.ui.home

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import io.alramdhan.lanadi.navigation.BottomNavScreen
import io.alramdhan.lanadi.navigation.NavigationGraph
import io.alramdhan.lanadi.navigation.ScreenTabItem
import io.alramdhan.lanadi.ui.components.BottomCurvedBar
import io.alramdhan.lanadi.ui.home.setting.SettingScreen
import io.alramdhan.lanadi.viewmodels.home.HomeViewModel
import io.alramdhan.lanadi.viewmodels.home.cart.CartViewModel
import io.alramdhan.lanadi.viewmodels.home.setting.SettingViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.MenuTab(
    widthSizeClass: WindowWidthSizeClass? = null,
    navController: NavController,
    animatedVisibilityScope: AnimatedVisibilityScope,
    homeVModel: HomeViewModel = koinViewModel(),
    settingVModel: SettingViewModel = koinViewModel(),
    cartViewModel: CartViewModel = koinViewModel()
) {
    val listTabScreen = listOf(
        ScreenTabItem(BottomNavScreen.Home.route, {
            HomeScreen(
                widthSizeClass = widthSizeClass,
                navController = navController,
                viewModel = homeVModel,
                cartViewModel = cartViewModel
            )}
        ),
        ScreenTabItem(BottomNavScreen.Order.route, {
            OrderScreen(
                widthSizeClass = widthSizeClass,
                navController = navController,
            )
        }),
        ScreenTabItem(BottomNavScreen.History.route, {
            HistoryScreen(
                widthSizeClass = widthSizeClass,
                navController = navController,
            )
        }),
        ScreenTabItem(BottomNavScreen.Setting.route, {
            SettingScreen(
                widthSizeClass = widthSizeClass,
                navController = navController,
                viewModel = settingVModel
            )
        })
    )
    val bottomNavController = rememberNavController()

    Scaffold(
        bottomBar = {
            // bottom bar
            BottomCurvedBar(bottomNavController, navController, animatedVisibilityScope)
        }
    ) { paddingValues ->
        Box(Modifier.padding(paddingValues)) {
            NavigationGraph(
                bottomNavController = bottomNavController,
                listScreen = listTabScreen
            )
        }
    }
}

@Composable
private fun BottomBar(bottomNavController: NavController) {
    val navigationItems = listOf(
        BottomNavScreen.Home,
        BottomNavScreen.Order,
        BottomNavScreen.History,
        BottomNavScreen.Setting
    )
    val navBackStackEntry by bottomNavController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar(
        modifier = Modifier
            .drawBehind {
                drawRect(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black),
                        startY = -30f,
                        endY = size.height
                    ),
                    topLeft = Offset(0f, -30f)
                )
            }
            .clip(
                shape = RoundedCornerShape(
                    topStart = 20.dp,
                    topEnd = 20.dp
                )
            )
            .navigationBarsPadding(),
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
}

@Composable
fun OrderScreen(
    widthSizeClass: WindowWidthSizeClass?,
    navController: NavController
) {

}

@Composable
fun HistoryScreen(
    widthSizeClass: WindowWidthSizeClass?,
    navController: NavController
) {

}