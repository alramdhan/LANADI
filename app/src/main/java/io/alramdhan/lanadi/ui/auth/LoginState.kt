package io.alramdhan.lanadi.ui.auth

import io.alramdhan.lanadi.domain.models.Kasir

data class LoginState(
    val isLoading: Boolean = false,
    val kasir: Kasir? = null,
    val error: String? = null
)