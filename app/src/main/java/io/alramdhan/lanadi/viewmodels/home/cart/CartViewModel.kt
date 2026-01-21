package io.alramdhan.lanadi.viewmodels.home.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.alramdhan.lanadi.domain.models.CartProduk
import io.alramdhan.lanadi.domain.usecase.AddToCartUseCase
import io.alramdhan.lanadi.domain.usecase.GetCartUseCase
import io.alramdhan.lanadi.ui.home.cart.CartEffect
import io.alramdhan.lanadi.ui.home.cart.CartIntent
import io.alramdhan.lanadi.ui.home.cart.CartState
import io.alramdhan.lanadi.ui.home.produk.FlyingItem
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CartViewModel(
    private val getCart: GetCartUseCase,
    private val addToCart: AddToCartUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(CartState())
    val uiState = _uiState.asStateFlow()

    private val _effect = Channel<CartEffect>()
    val effect = _effect.receiveAsFlow()

    init {
        onIntent(CartIntent.LoadCart)
    }

    fun onIntent(intent: CartIntent) {
        when(intent) {
            is CartIntent.UpdateCartPosition -> _uiState.update { it.copy(cartIconPosition = intent.position) }
            is CartIntent.LoadCart -> fetchCart()
            is CartIntent.AddItem -> {
                val newItem = FlyingItem(
                    product = intent.item,
                    image = intent.painter,
                    startPosition = intent.startPosition,
                    startSize = intent.startSize,
                    targetPosition = _uiState.value.cartIconPosition
                )
                _uiState.update {
                    it.copy(flyingItems = it.flyingItems + newItem)
                }
                addItem(intent.item)
            }
            is CartIntent.AnimationFinished -> {
                _uiState.update { it.copy(flyingItems = it.flyingItems.filter { fi -> fi.id != intent.flyingItemsId }) }
            }
        }
    }

    private fun fetchCart() {
        viewModelScope.launch {
            getCart().collect { result ->
                _uiState.update { it.copy(products = result) }
            }
        }
    }

    private fun addItem(item: CartProduk) {
        viewModelScope.launch {
            delay(1000)
            addToCart(item)
            _effect.send(CartEffect.ShowToast("Berhasil menambahkan ke cart"))
        }
    }
}