package io.alramdhan.lanadi.ui.home

import io.alramdhan.lanadi.domain.models.Kategori

data class HomeState(
    val isKategoriLoading: Boolean = false,
    val kategoris: List<Kategori> = emptyList(),
    val selectedKategori: Int = 0,
    val errorKategori: String? = null,

    val searchMenu: String? = null
)
