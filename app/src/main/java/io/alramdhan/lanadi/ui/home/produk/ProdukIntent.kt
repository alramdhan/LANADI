package io.alramdhan.lanadi.ui.home.produk

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntSize
import io.alramdhan.lanadi.domain.models.Produk

sealed class ProdukIntent {
    data class AddToCart(val produk: Produk, val coords: Offset, val cardSize: IntSize): ProdukIntent()
    data object AnimationFinished: ProdukIntent()
}