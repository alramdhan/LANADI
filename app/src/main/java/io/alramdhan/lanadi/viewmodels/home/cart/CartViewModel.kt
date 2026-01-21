package io.alramdhan.lanadi.viewmodels.home.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.alramdhan.lanadi.domain.models.CartProduk
import io.alramdhan.lanadi.domain.usecase.AddToCartUseCase
import io.alramdhan.lanadi.domain.usecase.GetCartUseCase
import io.alramdhan.lanadi.ui.home.cart.CartIntent
import io.alramdhan.lanadi.ui.home.cart.CartState
import io.alramdhan.lanadi.ui.home.produk.ProdukState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CartViewModel(
    private val getCart: GetCartUseCase,
    private val addToCart: AddToCartUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(CartState())
    val uiState = _uiState.asStateFlow()

    private val _produkState = MutableStateFlow(ProdukState())
    val produkState = _produkState.asStateFlow()

    init {
        onIntent(CartIntent.LoadCart)
    }

    fun onIntent(intent: CartIntent) {
        when(intent) {
            is CartIntent.LoadCart -> fetchCart()
            is CartIntent.AddItem -> {
                _produkState.update {
                    it.copy(startCoords = intent.coords, startSize = intent.cardSize, isAnimating = true, image = intent.painter)
                }
                addItem(intent.item)
            }
            is CartIntent.AnimationFinished -> _produkState.update { it.copy(isAnimating = false) }
        }
    }

    private fun fetchCart() {
        viewModelScope.launch {
            getCart().collect { result ->
                _uiState.update { it.copy(item = result) }
            }
        }
    }

    private fun addItem(item: CartProduk) {
        viewModelScope.launch {
            delay(1000)
            addToCart(item)
        }
    }
}