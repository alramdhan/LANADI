package io.alramdhan.lanadi.ui.home.checkout

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import io.alramdhan.lanadi.core.utils.toRupiah
import io.alramdhan.lanadi.domain.models.MetodePembayaran
import io.alramdhan.lanadi.ui.components.LanadiButton
import io.alramdhan.lanadi.ui.components.PaymentOptionCard
import io.alramdhan.lanadi.viewmodels.home.checkout.CheckoutViewModel

@Composable
fun CheckoutScreen(viewModel: CheckoutViewModel, navController: NavController) {
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when(effect) {
                is CheckoutEffect.NavigateToSuccess -> navController.navigate("")
                is CheckoutEffect.ShowError -> Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
    ) {
        Text("Pilih Metode Pembayaran")
        Spacer(Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Jumlah yang harus dibayar: ",
                color = Color.Gray,
                fontSize = 12.sp
            )
            Text(state.totalPrice.toRupiah(),
                color = MaterialTheme.colorScheme.primary,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(Modifier.height(16.dp))
        LazyColumn(Modifier.fillMaxWidth()) {
            items(
                items = MetodePembayaran.entries,
                key = { it }
            ) { metode ->
                PaymentOptionCard(
                    metode,
                    isSelected = state.selectedMethod == metode,
                    onClick = { viewModel.onIntent(CheckoutIntent.SelectMethod(metode)) }
                )
            }
        }
        Spacer(Modifier.height(16.dp))
        LanadiButton(
            onClick = { },
            modifier = Modifier.fillMaxWidth(),
            enabled = state.selectedMethod != null && !state.isLoading
        ) {
            if (state.isLoading) CircularProgressIndicator(color = Color.White)
            else Text(
                "Proses Pembayaran",
                style = MaterialTheme.typography.titleSmall.copy(color = Color.White)
            )
        }
    }
}
