package io.alramdhan.lanadi.viewmodels.home.checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.alramdhan.lanadi.domain.models.MetodePembayaran
import io.alramdhan.lanadi.ui.home.cart.CartState
import io.alramdhan.lanadi.ui.home.checkout.CheckoutEffect
import io.alramdhan.lanadi.ui.home.checkout.CheckoutIntent
import io.alramdhan.lanadi.ui.home.checkout.CheckoutState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CheckoutViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(CheckoutState())
    val uiState = _uiState.asStateFlow()

    private val _effect = Channel<CheckoutEffect>()
    val effect = _effect.receiveAsFlow()

    fun updateCartData(newState: CartState) {
        _uiState.update { it.copy(products = newState.products, namaPelanggan = newState.namaPelanggan, totalPrice = newState.totalPrice) }
    }

    fun onIntent(intent: CheckoutIntent) {
        when(intent) {
            is CheckoutIntent.SelectMethod -> {
                _uiState.update { it.copy(selectedMethod = intent.method) }
            }
            is CheckoutIntent.ConfirmPayment -> confirmPayment()
        }
    }

    private fun confirmPayment() {
        viewModelScope.launch {

        }
    }
}