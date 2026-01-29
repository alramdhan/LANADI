package io.alramdhan.lanadi.ui.home.cart

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.IntSize
import io.alramdhan.lanadi.domain.models.CartProduk
import io.alramdhan.lanadi.ui.animations.FlyingItem

sealed class CartEffect {
    data class ShowToast(val message: String): CartEffect()
    data object NavigateToCheckout: CartEffect()
}

sealed class CartIntent {
    data class UpdateCartPosition(val position: Offset): CartIntent()

    data object LoadCart: CartIntent()
    data class AddItem(val item: CartProduk, val startPosition: Offset, val startSize: IntSize, val painter: Painter): CartIntent()
    data class UpdateQty(val productId: Int, val qty: Int): CartIntent()
    data class DeleteItem(val item: CartProduk): CartIntent()
    data object DeleteAllItems: CartIntent()

    data class OnChangeNamaPelanggan(val nama: String): CartIntent()

    data object CheckoutClicked: CartIntent()

    data class AnimationFinished(val flyingItemsId: Long): CartIntent()
}

data class CartState(
    val isCartLoading: Boolean = false,
    val products: List<CartProduk> = emptyList(),
    val cartCount: Int = 0,
    val flyingItems: List<FlyingItem> = emptyList(),
    val cartIconPosition: Offset = Offset.Zero,
    val namaPelanggan: String = "",
) {
    val totalPrice: Double
        get() = products.sumOf { it.totalPrice }
}