package io.alramdhan.lanadi.ui.auth

sealed class LoginIntent {
    data class LoginCLicked(val login: String, val password: String): LoginIntent()
    data class LoginChanged(val login: String): LoginIntent()
    data class PasswordChanged(val pass: String): LoginIntent()
    data class PasswordVisibilityChanged(val visibility: Boolean) : LoginIntent()
    data object ErrorDismissed : LoginIntent()
}