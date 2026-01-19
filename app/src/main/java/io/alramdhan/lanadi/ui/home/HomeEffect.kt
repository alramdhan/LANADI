package io.alramdhan.lanadi.ui.home

sealed class HomeEffect {
    data class ShowToastMessage(val message: String): HomeEffect()
}