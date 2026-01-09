package io.alramdhan.lanadi.viewmodels.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.alramdhan.lanadi.domain.usecase.LoginUseCase
import io.alramdhan.lanadi.ui.auth.LoginEffect
import io.alramdhan.lanadi.ui.auth.LoginIntent
import io.alramdhan.lanadi.ui.auth.LoginState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val loginUseCase: LoginUseCase) : ViewModel() {
    var state by mutableStateOf(LoginState())
        private set
    private val _effect = Channel<LoginEffect>()
    val effect = _effect.receiveAsFlow()

    fun onIntent(intent: LoginIntent) {
        when (intent) {
            is LoginIntent.LoginCLicked -> executeLogin(intent.email, intent.password)
            is LoginIntent.ErrorDismissed -> state = state.copy(error = null)
        }
    }

    private fun executeLogin(email: String, pass: String) {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            loginUseCase(email, pass)
                .onSuccess {
                    state = state.copy(isLoading = false, kasir = it)
                    _effect.send(LoginEffect.NavigateToHome)
                }.onFailure {
                    state = state.copy(isLoading = false, error = it.message)
                }
        }
    }
}