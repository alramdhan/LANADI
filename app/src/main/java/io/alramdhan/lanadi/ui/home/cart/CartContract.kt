package io.alramdhan.lanadi.ui.home.cart

import io.alramdhan.lanadi.domain.models.CartProduk
import io.alramdhan.lanadi.navigation.ScreenTabItem

sealed class CartEffect {
    data class ShowToast(val message: String): CartEffect()
}

sealed class CartIntent {
    data object LoadCart: CartIntent()
    data class AddItem(val item: CartProduk): CartIntent()
}

data class CartState(
    val item: List<CartProduk> = emptyList(),
    val total: Double = 0.0
)