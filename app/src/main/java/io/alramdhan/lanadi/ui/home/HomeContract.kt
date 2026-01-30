package io.alramdhan.lanadi.ui.home

import io.alramdhan.lanadi.core.ui.UiState
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
    val kategoris: UiState<List<Kategori>> = UiState.Idle,
    val selectedKategori: Int = 0,

    val produks: UiState<List<Produk>> = UiState.Idle,

    val searchText: String? = null
)