package io.alramdhan.lanadi.viewmodels.home.checkout

import androidx.lifecycle.ViewModel
import io.alramdhan.lanadi.ui.home.cart.CartState
import io.alramdhan.lanadi.ui.home.checkout.CheckoutState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CheckoutViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(CheckoutState())
    val uiState = _uiState.asStateFlow()

    fun updateCartData(newState: CartState) {
        _uiState.update { it.copy(products = newState.products, namaPelanggan = newState.namaPelanggan, totalPrice = newState.totalPrice) }
    }
}