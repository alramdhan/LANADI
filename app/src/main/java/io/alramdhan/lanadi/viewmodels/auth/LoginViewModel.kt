package io.alramdhan.lanadi.viewmodels.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.alramdhan.lanadi.domain.usecase.LoginUseCase
import io.alramdhan.lanadi.ui.auth.LoginIntent
import io.alramdhan.lanadi.ui.auth.LoginState
import kotlinx.coroutines.launch

class LoginViewModel(private val loginUseCase: LoginUseCase) : ViewModel() {
    var state by mutableStateOf(LoginState())
        private set

    fun onIntent(intent: LoginIntent) {
        when (intent) {
            is LoginIntent.LoginCLicked -> login(intent.email, intent.password)
        }
    }

    private fun login(email: String, pass: String) {
        viewModelScope.launch {
            state = state.copy(isLoading = true, error = null)
            loginUseCase(email, pass)
                .onSuccess { state = state.copy(isLoading = false, kasir = it) }
                .onFailure { state = state.copy(isLoading = false, error = it.message) }
        }
    }
}