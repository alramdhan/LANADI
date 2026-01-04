package io.alramdhan.lanadi.ui.auth

sealed class LoginIntent {
    data class LoginCLicked(val email: String, val password: String): LoginIntent()
}