package io.alramdhan.lanadi.ui.home

sealed class HomeEffect {
    data class ShowSnackBar(val message: String): HomeEffect()
}