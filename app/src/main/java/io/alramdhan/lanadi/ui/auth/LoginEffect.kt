package io.alramdhan.lanadi.ui.auth

sealed class LoginEffect {
    data object NavigateToHome : LoginEffect()
    data class ShowSnackbar(val message: String) : LoginEffect()
}