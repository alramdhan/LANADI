package io.alramdhan.lanadi.ui.home.setting

import android.annotation.SuppressLint
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import io.alramdhan.lanadi.navigation.Screen
import io.alramdhan.lanadi.viewmodels.home.setting.SettingViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SettingScreen(
    widthSizeClass: WindowWidthSizeClass?,
    navController: NavController,
    viewModel: SettingViewModel
) {
    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(key1 = true) {
        viewModel.effect.collect { effect ->
            when(effect) {
                is SettingEffect.NavigateToLogin -> {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Main.route) {
                            inclusive = true
                        }
                    }
                }
                is SettingEffect.ShowSnackBar -> snackbarHostState.showSnackbar(effect.message)
            }
        }
    }

    Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }) { _ ->
        LazyColumn(Modifier.fillMaxSize()) {
            item(1) {
                ListItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(
                            interactionSource = remember(Unit) { MutableInteractionSource() },
                            indication = LocalIndication.current,
                            onClick = {
                                viewModel.onIntent(SettingIntent.LogoutClicked)
                            }
                        ),
                    headlineContent = { Text("Keluar") },
                    trailingContent = {
                        Icon(
                            Icons.AutoMirrored.Filled.Logout,
                            contentDescription = "icon keluar"
                        )
                    }
                )
            }
        }
    }
}