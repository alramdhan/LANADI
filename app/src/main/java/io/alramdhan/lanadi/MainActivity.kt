package io.alramdhan.lanadi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import io.alramdhan.lanadi.core.di.appModule
import io.alramdhan.lanadi.core.di.networkModule
import io.alramdhan.lanadi.navigation.NavigationStack
import io.alramdhan.lanadi.ui.theme.LANADITheme
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startKoin {
            androidContext(this@MainActivity)
            modules(networkModule)
            modules(appModule)
        }
        enableEdgeToEdge()
        setContent {
            val windowSizedClass = calculateWindowSizeClass(this)
            LANADITheme(dynamicColor = false) {
                NavigationStack(windowWidthSizeClass = windowSizedClass.widthSizeClass)
            }
        }
    }
}
