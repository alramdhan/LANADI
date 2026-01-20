package io.alramdhan.lanadi.ui.home.produk

import androidx.compose.ui.geometry.Offset

data class ProdukState(
    val isAnimating: Boolean = false,
    val startCoords: Offset = Offset.Zero
)