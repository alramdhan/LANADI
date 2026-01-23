package io.alramdhan.lanadi.ui.animations

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.IntSize
import io.alramdhan.lanadi.domain.models.CartProduk

data class FlyingItem(
//    val isAnimating: Boolean = false,
    val image: Painter? = null,
    val id: Long = System.nanoTime(),
    val product: CartProduk,
    val startPosition: Offset,
    val startSize: IntSize,
    val targetPosition: Offset
)