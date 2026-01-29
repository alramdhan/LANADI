package io.alramdhan.lanadi.ui.home

import io.alramdhan.lanadi.domain.models.Kategori
import io.alramdhan.lanadi.domain.models.Produk

sealed class HomeEffect {
    data class ShowToastMessage(val message: String): HomeEffect()
}

sealed class HomeIntent {
    data object LoadDataHome: HomeIntent()
    data class OnSelectKategori(val id: Int): HomeIntent()

    data class SearchTextChanged(val text: String): HomeIntent()
    data object ResetSearch: HomeIntent()
}

data class HomeState(
    val isKategoriLoading: Boolean = false,
    val kategoris: List<Kategori> = emptyList(),
    val selectedKategori: Int = 0,
    val errorKategori: String? = null,

    val isProdukLoading: Boolean = false,
    val produks: List<Produk> = emptyList(),
    val filterProduks: List<Produk> = emptyList(),
    val errorProduk: String? = null,

    val searchText: String? = null
)