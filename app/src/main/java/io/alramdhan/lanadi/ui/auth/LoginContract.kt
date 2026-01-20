package io.alramdhan.lanadi.ui.auth

import io.alramdhan.lanadi.domain.models.CurrentUser

sealed class LoginEffect {
    data object NavigateToHome : LoginEffect()
    data class ShowSnackbar(val message: String) : LoginEffect()
}

// intent
sealed class LoginIntent {
    data class LoginCLicked(val login: String, val password: String): LoginIntent()
    data class LoginChanged(val login: String): LoginIntent()
    data class PasswordChanged(val pass: String): LoginIntent()
    data class PasswordVisibilityChanged(val visibility: Boolean) : LoginIntent()
    data object ErrorDismissed : LoginIntent()
}

// state
data class LoginState(
    val isLoading: Boolean = false,
    val kasir: CurrentUser? = null,
    val error: String? = null,

    val login: String = "",
    val password: String = "",
    val loginError: Boolean = false,
    val passwordError: Boolean = false,
    val loginMsgError: String? = null,
    val passwordMsgError: String? = null,
    val passwordVisibility: Boolean = true,
)