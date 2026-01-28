package io.alramdhan.lanadi.viewmodels.home.checkout

import androidx.lifecycle.ViewModel
import io.alramdhan.lanadi.ui.home.cart.CartState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CheckoutViewModel : ViewModel() {
    private val _cartState = MutableStateFlow(CartState())
    val cartState = _cartState.asStateFlow()

    fun updateCartData(newState: CartState) {
        _cartState.value = newState
    }
}