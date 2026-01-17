package io.alramdhan.lanadi.ui.auth

import io.alramdhan.lanadi.domain.models.CurrentUser

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