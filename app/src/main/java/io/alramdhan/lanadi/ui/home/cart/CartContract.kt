package io.alramdhan.lanadi.ui.home.cart

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.IntSize
import io.alramdhan.lanadi.domain.models.CartProduk
import io.alramdhan.lanadi.ui.home.produk.FlyingItem

sealed class CartEffect {
    data class ShowToast(val message: String): CartEffect()
}

sealed class CartIntent {
    data class UpdateCartPosition(val position: Offset): CartIntent()

    data object LoadCart: CartIntent()
    data class AddItem(val item: CartProduk, val startPosition: Offset, val startSize: IntSize, val painter: Painter): CartIntent()

    data class AnimationFinished(val flyingItemsId: Long): CartIntent()
}

data class CartState(
    val products: List<CartProduk> = emptyList(),
    val cartCount: Int = 0,
    val flyingItems: List<FlyingItem> = emptyList(),
    val cartIconPosition: Offset = Offset.Zero
)