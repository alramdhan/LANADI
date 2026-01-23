package io.alramdhan.lanadi.ui.home.cart

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import io.alramdhan.lanadi.viewmodels.home.cart.CartViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun CartScreen(
    widthSizeClass: WindowWidthSizeClass?,
    navController: NavController,
    cartViewModel: CartViewModel = koinViewModel(),
) {
    Column(Modifier.safeDrawingPadding()) {
        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            IconButton({ navController.popBackStack() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "icon navigate pop")
            }
            Text(
                "Keranjang",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(Modifier.size(0.dp))
        }
    }
}