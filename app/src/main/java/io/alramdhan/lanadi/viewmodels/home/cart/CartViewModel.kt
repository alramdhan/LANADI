package io.alramdhan.lanadi.viewmodels.home.cart

import androidx.lifecycle.ViewModel
import io.alramdhan.lanadi.domain.usecase.AddToCartUseCase
import io.alramdhan.lanadi.domain.usecase.GetCartUseCase
import io.alramdhan.lanadi.ui.home.cart.CartIntent
import io.alramdhan.lanadi.ui.home.cart.CartState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CartViewModel(
    private val getCart: GetCartUseCase,
    private val addToCart: AddToCartUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(CartState())
    val uiState = _uiState.asStateFlow()

    fun onIntent(intent: CartIntent) {

    }
}