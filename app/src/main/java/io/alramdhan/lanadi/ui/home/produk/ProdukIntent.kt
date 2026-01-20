package io.alramdhan.lanadi.ui.home.produk

import androidx.compose.ui.geometry.Offset
import io.alramdhan.lanadi.domain.models.Produk

sealed class ProdukIntent {
    data class AddToCart(val produk: Produk, val coords: Offset): ProdukIntent()
    data object AnimationFinished: ProdukIntent()
}