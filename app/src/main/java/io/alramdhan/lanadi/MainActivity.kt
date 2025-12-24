package io.alramdhan.lanadi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import io.alramdhan.lanadi.navigation.NavigationStack
import io.alramdhan.lanadi.ui.auth.LoginScreen
import io.alramdhan.lanadi.ui.theme.LANADITheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val windowSizedClass = calculateWindowSizeClass(this)
            LANADITheme(dynamicColor = false) {
                NavigationStack(windowWidthSizeClass = windowSizedClass.widthSizeClass)
            }
        }
    }
}
