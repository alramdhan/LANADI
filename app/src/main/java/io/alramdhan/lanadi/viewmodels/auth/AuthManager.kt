package io.alramdhan.lanadi.viewmodels.auth

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class AuthManager {
    private val _onUnauthorized = MutableSharedFlow<Boolean>()
    val onUnauthorized = _onUnauthorized.asSharedFlow()

    suspend fun logout() {
        _onUnauthorized.emit(true)
    }
}