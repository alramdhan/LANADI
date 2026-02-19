package io.alramdhan.lanadi.ui.home.setting

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Outlet
import androidx.compose.material.icons.filled.Print
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemColors
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import io.alramdhan.lanadi.navigation.Screen
import io.alramdhan.lanadi.ui.theme.Danger
import io.alramdhan.lanadi.viewmodels.home.setting.SettingViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SettingScreen(
    widthSizeClass: WindowWidthSizeClass?,
    navController: NavController,
    viewModel: SettingViewModel
) {
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val listItemColors = ListItemDefaults.colors(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
    )

    LaunchedEffect(key1 = true) {
        viewModel.effect.collect { effect ->
            when(effect) {
                is SettingEffect.NavigateToLogin -> {
                    navController.navigate(Screen.Login.route) {
                        Toast.makeText(context, "Anda telah keluar", Toast.LENGTH_LONG).show()
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
        Column(Modifier.fillMaxSize()) {
            Box(Modifier.padding(vertical = 8.dp, horizontal = 16.dp)) {
                Text(
                    "Pengaturan",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Spacer(Modifier.height(8.dp))
            ListItem(
                colors = listItemColors,
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(16.dp)),
                leadingContent = {
                    Icon(
                        Icons.Default.Outlet,
                        contentDescription = "icon outlet",
                        tint = Color.White,
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.primary, CircleShape)
                            .size(50.dp)
                    )
                },
                headlineContent = {
                    Text("Nama Toko",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                supportingContent = {
                    Text("Alamat toko",
                        color = Color.DarkGray
                    )
                },
                trailingContent = {
                    Icon(Icons.Default.ChevronRight, contentDescription = "chevron right")
                }
            )
            Box(Modifier.padding(vertical = 8.dp, horizontal = 16.dp)) {
                Text("Perangkat",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Gray
                )
            }
            CardBoxSetting(Modifier.padding(horizontal = 16.dp)) {
                ItemSetting(
                    label = "Printer",
                    leadingIcon = Icons.Default.Print,
                    colors = listItemColors
                ) { }
            }
            Spacer(Modifier.height(24.dp))
            ListItem(
                colors = listItemColors,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .clickable(
                        interactionSource = remember(Unit) { MutableInteractionSource() },
                        indication = LocalIndication.current,
                        onClick = {
                            viewModel.onIntent(SettingIntent.LogoutClicked)
                        }
                    ),
                headlineContent = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text("Keluar", color = Danger)
                        Spacer(Modifier.width(24.dp))
                        Icon(Icons.AutoMirrored.Filled.Logout, contentDescription = "ic logout", tint = Danger)
                    }
                },
            )
        }
    }
}

@Composable
private fun CardBoxSetting(
    modifier: Modifier = Modifier,
    content: @Composable (ColumnScope.() -> Unit)
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        content = content
    )
}

@Composable
private fun ItemSetting(
    label: String,
    leadingIcon: ImageVector,
    trailingIcon: ImageVector = Icons.Default.ChevronRight,
    colors: ListItemColors = ListItemDefaults.colors(),
    onClick: () -> Unit
) {
    ListItem(
        colors = colors,
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primaryContainer)
            .clickable(
                interactionSource = remember(Unit) { MutableInteractionSource() },
                indication = LocalIndication.current,
                onClick = onClick
            )
            .padding(horizontal = 5.dp, vertical = 12.dp),
        leadingContent = {
            Box(
                Modifier
                    .background(
                        MaterialTheme.colorScheme.primary.copy(.25f),
                        RoundedCornerShape(12.dp)
                    )
                    .border(border = BorderStroke(1.dp, color = MaterialTheme.colorScheme.primary), shape = RoundedCornerShape(12.dp))
                    .padding(8.dp)
            ) {
                Icon(
                    leadingIcon,
                    contentDescription = "ic printer",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        },
        headlineContent = { Text(label, fontWeight = FontWeight.Bold) },
        trailingContent = {
            Icon(
                trailingIcon,
                contentDescription = "icon keluar"
            )
        }
    )
}
