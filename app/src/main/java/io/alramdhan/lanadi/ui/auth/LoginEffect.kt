package io.alramdhan.lanadi.ui.auth

sealed class LoginEffect {
    data object NavigateToHome : LoginEffect()
    data class ShowSnacbar(val message: String) : LoginEffect()
}