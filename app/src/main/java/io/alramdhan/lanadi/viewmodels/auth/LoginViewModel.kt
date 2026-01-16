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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(private val loginUseCase: LoginUseCase) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginState())
    val uiState = _uiState.asStateFlow()

    private val _effect = Channel<LoginEffect>()
    val effect = _effect.receiveAsFlow()

    fun onIntent(intent: LoginIntent) {
        when (intent) {
            is LoginIntent.LoginCLicked -> {
                when(formValidation()) {
                    false -> executeLogin()
                    else -> {}
                }
            }
            is LoginIntent.LoginChanged -> _uiState.update { it.copy(login = intent.login)  }
            is LoginIntent.PasswordChanged -> _uiState.update { it.copy(password = intent.pass) }
            is LoginIntent.PasswordVisibilityChanged -> _uiState.update { it.copy(passwordVisibility = intent.visibility) }
            is LoginIntent.ErrorDismissed -> _uiState.update { it.copy(error = null) }
        }
    }

    private fun formValidation(): Boolean {
        if(_uiState.value.login.isEmpty()) {
            _uiState.update {
                it.copy(loginError = true, loginMsgError = "Email atau Username harus diisi")
            }
            return true
        }
        if(_uiState.value.password.isEmpty()) {
            _uiState.update {
                it.copy(passwordError = true, passwordMsgError = "Password harus diisi")
            }
            return true
        } else if(_uiState.value.password.length < 8) {
            _uiState.update {
                it.copy(passwordError = true, passwordMsgError = "Password minimal 8 karakter")
            }
            return true
        }

        _uiState.update {
            it.copy(
                loginError = false,
                loginMsgError = "",
                passwordError = false,
                passwordMsgError = ""
            )
        }
        return false
    }

    private fun executeLogin() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            loginUseCase(_uiState.value.login, _uiState.value.password)
                .onSuccess {
                    _uiState.update { state -> state.copy(isLoading = false, kasir = it) }
                    _effect.send(LoginEffect.NavigateToHome)
                }.onFailure {
                    _uiState.update { state -> state.copy(isLoading = false, error = it.message) }
                    _effect.send(LoginEffect.ShowSnackbar(it.message!!))
                }
        }
    }
}