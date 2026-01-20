package io.alramdhan.lanadi.ui.home

import io.alramdhan.lanadi.domain.models.Kategori
import io.alramdhan.lanadi.domain.models.Produk

data class HomeState(
    val isKategoriLoading: Boolean = false,
    val kategoris: List<Kategori> = emptyList(),
    val selectedKategori: Int = 0,
    val errorKategori: String? = null,

    val isProdukLoading: Boolean = false,
    val produks: List<Produk> = emptyList(),
    val errorProduk: String? = null,

    val searchMenu: String? = null
)
