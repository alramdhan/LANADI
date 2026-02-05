package io.alramdhan.lanadi.ui.home.checkout

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.navigation.NavController
import io.alramdhan.lanadi.viewmodels.home.checkout.CheckoutViewModel

@Composable
fun CheckoutScreen(viewModel: CheckoutViewModel, navController: NavController) {
    val state by viewModel.uiState.collectAsState()

    Box(
        contentAlignment = Alignment.Center
    ) {
        Text("name ${state.namaPelanggan}")
    }
}