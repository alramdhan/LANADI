package io.alramdhan.lanadi.ui.home.setting

sealed class SettingEffect {
    data object NavigateToLogin : SettingEffect()
    data class ShowSnackBar(val message: String): SettingEffect()
}

sealed class SettingIntent {
    data object LogoutClicked: SettingIntent()
}

data class SettingState(val message: String)