package io.alramdhan.lanadi.ui.home.produk

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.IntSize
import io.alramdhan.lanadi.domain.models.CartProduk
import io.alramdhan.lanadi.domain.models.Produk

data class FlyingItem(
//    val isAnimating: Boolean = false,
    val image: Painter? = null,
//    val startCoords: Offset = Offset.Zero,
//    val startSize: IntSize = IntSize.Zero,
//    val targetSize: IntSize = IntSize.Zero
    val id: Long = System.nanoTime(),
    val product: CartProduk,
    val startPosition: Offset,
    val startSize: IntSize,
    val targetPosition: Offset
)