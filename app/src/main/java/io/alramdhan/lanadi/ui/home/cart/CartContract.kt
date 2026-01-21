package io.alramdhan.lanadi.ui.home.cart

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntSize
import io.alramdhan.lanadi.domain.models.CartProduk

sealed class CartEffect {
    data class ShowToast(val message: String): CartEffect()
}

sealed class CartIntent {
    data object LoadCart: CartIntent()
    data class AddItem(val item: CartProduk, val coords: Offset, val cardSize: IntSize): CartIntent()

    data object AnimationFinished: CartIntent()
}

data class CartState(
    val item: List<CartProduk> = emptyList(),
    val total: Double = 0.0
)