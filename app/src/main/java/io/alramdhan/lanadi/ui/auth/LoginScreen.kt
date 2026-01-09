package io.alramdhan.lanadi.ui.auth

import android.content.pm.ActivityInfo
import android.util.Log
import androidx.activity.compose.LocalActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import io.alramdhan.lanadi.R
import io.alramdhan.lanadi.core.constants.AppString
import io.alramdhan.lanadi.navigation.Screen
import io.alramdhan.lanadi.ui.widgets.ModernLanButton
import io.alramdhan.lanadi.ui.widgets.LanadiTextField
import io.alramdhan.lanadi.viewmodels.auth.LoginViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.LoginScreen(
    windowWidthSizeClass: WindowWidthSizeClass?,
    navController: NavController,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    var showContent by remember { mutableStateOf(false) }
    val meshGradient = Brush.linearGradient(
        colors = listOf(
            MaterialTheme.colorScheme.primary.copy(alpha = .4f),
            MaterialTheme.colorScheme.secondary.copy(alpha = .2f)
        ),
    )

    LaunchedEffect(Unit) {
        showContent = true
    }

    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = meshGradient)
                .padding(paddingValues)
        ) {
            when(windowWidthSizeClass) {
                WindowWidthSizeClass.Compact -> MobileLayout(showContent, navController, animatedVisibilityScope)
                WindowWidthSizeClass.Medium -> TabletLayout(showContent, navController, animatedVisibilityScope)
                WindowWidthSizeClass.Expanded -> TabletLayout(showContent, navController, animatedVisibilityScope)
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.MobileLayout(showContent: Boolean = false, navController: NavController, animatedVisibilityScope: AnimatedVisibilityScope) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedVisibility(
            visible = showContent,
            enter = fadeIn(
                animationSpec = tween(700)
            ) + slideInVertically(
                initialOffsetY = { -it / 2 },
                animationSpec = tween(700)
            )
        ) {
            LogoSection()
        }
        Spacer(Modifier.height(10.dp))
        AnimatedVisibility(
            visible = showContent,
            enter = fadeIn(
                animationSpec = tween(700, delayMillis = 200)
            ) + slideInVertically(
                initialOffsetY = { -it / 2 },
                animationSpec = tween(700, delayMillis = 200)
            )
        ) {
            Box {
                LoginForm(
                    animatedVisibilityScope = animatedVisibilityScope,
                    onNavigateHome = {
                        navController.navigate(Screen.Main.route) {
                            popUpTo(Screen.Login.route) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.TabletLayout(showContent: Boolean = false, navController: NavController, animatedVisibilityScope: AnimatedVisibilityScope) {
    val activity = LocalActivity.current
    LaunchedEffect(Unit) {
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .widthIn(max = 900.dp)
            .padding(horizontal = 48.dp, vertical = 24.dp)
            .clip(RoundedCornerShape(24.dp)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        AnimatedVisibility(
            visible = showContent,
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            enter = fadeIn(
                animationSpec = tween(700)
            ) + slideInHorizontally(
                initialOffsetX = { it / 2 },
                animationSpec = tween(700)
            )
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                LoginForm(
                    isTablet = true,
                    animatedVisibilityScope = animatedVisibilityScope,
                    onNavigateHome = {
                        navController.navigate(Screen.Main.route) {
                            popUpTo(Screen.Login.route) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
        }
        Spacer(Modifier.height(10.dp))
        AnimatedVisibility(
            visible = showContent,
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            enter = fadeIn(
                animationSpec = tween(700, delayMillis = 200)
            ) + slideInHorizontally(
                initialOffsetX = { -it / 2 },
                animationSpec = tween(700, delayMillis = 200)
            )
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                LogoSection(isInverse = true)
            }
        }
    }
}

@Composable
fun LogoSection(isInverse: Boolean = false) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(if (isInverse) 140.dp else 100.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(
                    MaterialTheme.colorScheme.primary
                ),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(R.drawable.main_logo),
                contentDescription = "App Logo",
                modifier = Modifier.size(
                    if(isInverse) 180.dp else 160.dp
                )
            )
        }
        Spacer(Modifier.height(16.dp))
        Text(AppString.APP_NAME,
            fontSize = if(isInverse) 38.sp else 28.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.sp,
            color = MaterialTheme.colorScheme.primary
        )
        Text("The Heartbeat of Your Transaction",
            fontStyle = FontStyle.Italic
        )
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.LoginForm(
    isTablet: Boolean = false,
    animatedVisibilityScope: AnimatedVisibilityScope,
    viewModel: LoginViewModel = koinViewModel(),
    onNavigateHome: () -> Unit
) {
    val state = viewModel.state
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when(effect) {
                is LoginEffect.NavigateToHome -> onNavigateHome()
                is LoginEffect.ShowSnacbar -> {
                    Log.d("LoginScreen", viewModel.state.error ?: "Terjadi kesalahan")
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp)
    ) {
        Text("Selamat Datang",
            fontSize = if(isTablet) 32.sp else 22.sp,
            fontWeight = FontWeight.Bold,
        )
        Text("Silahkan masuk ke akun Anda")
        Spacer(Modifier.height(16.dp))
        LanadiTextField(
            value = email,
            onValueChange = { email = it },
            label = "Email"
        )
        Spacer(Modifier.height(10.dp))
        LanadiTextField(
            value = password,
            onValueChange = { password = it },
            label = "Password"
        )
        Spacer(Modifier.height(20.dp))
        ModernLanButton(
            modifier = Modifier.sharedElement(
                rememberSharedContentState(key = "btn_login_to_fab"),
                animatedVisibilityScope = animatedVisibilityScope
            ),
            onClick = {
                viewModel.onIntent(LoginIntent.LoginCLicked(email, password))
            },
            isLoading = state.isLoading,
            text = "Masuk"
        )
    }
}

