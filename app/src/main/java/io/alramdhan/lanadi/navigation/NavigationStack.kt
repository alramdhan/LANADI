package io.alramdhan.lanadi.navigation

import android.Manifest
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import io.alramdhan.lanadi.core.helper.PermissionHelper
import io.alramdhan.lanadi.ui.auth.LoginScreen
import io.alramdhan.lanadi.ui.home.MenuTab
import io.alramdhan.lanadi.ui.home.feature.CameraQRScanner

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun NavigationStack(windowWidthSizeClass: WindowWidthSizeClass?, token: String?, onUnauthorized: Boolean?) {
    val context = LocalContext.current
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            Toast.makeText(context, "Izin Kamera Diberikan!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Izin Kamera Ditolak.", Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(Unit) {
        if(PermissionHelper.hasPermissions(context, Manifest.permission.CAMERA)) {
            println("sudah ada izin kamera")
        } else {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    SharedTransitionLayout {
        val navController = rememberNavController()
        val graph = navController.createGraph(startDestination = Screen.Initial.route) {
            composable(Screen.Initial.route) {
                if(token.isNullOrEmpty() || onUnauthorized == true) {
                    LoginScreen(
                        windowWidthSizeClass = windowWidthSizeClass,
                        navController = navController,
                        animatedVisibilityScope = this@composable,
                    )
                } else {
                    MenuTab(windowWidthSizeClass, navController, this@composable)
                }
            }
            composable(Screen.Login.route) {
                LoginScreen(
                    windowWidthSizeClass = windowWidthSizeClass,
                    navController = navController,
                    animatedVisibilityScope = this@composable,
                )
            }
            composable(route = Screen.Main.route) {
                MenuTab(windowWidthSizeClass, navController, this@composable)
            }
            composable(route = Screen.QRScanner.route) {
                CameraQRScanner(windowWidthSizeClass, navController)
            }
        }
        NavHost(
            navController = navController,
            graph = graph,
//            startDestination = Screen.Initial.route,
        )
    }
}