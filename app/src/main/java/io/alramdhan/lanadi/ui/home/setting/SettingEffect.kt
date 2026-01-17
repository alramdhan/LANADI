package io.alramdhan.lanadi.ui.home.setting

sealed class SettingEffect {
    data object NavigateToLogin : SettingEffect()
    data class ShowSnackBar(val message: String): SettingEffect()
}