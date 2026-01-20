package io.alramdhan.lanadi.ui.home.produk

sealed class ProdukEffect {
    data object ShowToast: ProdukEffect()
}