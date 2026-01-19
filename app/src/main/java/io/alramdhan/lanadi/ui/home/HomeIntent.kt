package io.alramdhan.lanadi.ui.home

sealed class HomeIntent {
    data object LoadCategories: HomeIntent()
    data class OnSelectKategori(val id: Int): HomeIntent()

    data class SearchTextChanged(val text: String): HomeIntent()
}