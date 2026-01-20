package io.alramdhan.lanadi.ui.home.produk

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntSize

data class ProdukState(
    val isAnimating: Boolean = false,
    val startCoords: Offset = Offset.Zero,
    val startSize: IntSize = IntSize.Zero,
    val targetSize: IntSize = IntSize.Zero
)