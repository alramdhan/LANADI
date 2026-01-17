package io.alramdhan.lanadi.ui.home.setting

sealed class SettingIntent {
    data object LogoutClicked: SettingIntent()
}