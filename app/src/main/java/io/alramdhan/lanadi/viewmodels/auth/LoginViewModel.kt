package io.alramdhan.lanadi.viewmodels.auth

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
                    false -> executeLogin(intent.login, intent.password)
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
        var valid = false

        if(_uiState.value.login.isEmpty()) {
            _uiState.update {
                it.copy(loginError = true, loginMsgError = "Email atau Username harus diisi")
            }
            valid = true
        }
        if(_uiState.value.password.isEmpty()) {
            _uiState.update {
                it.copy(passwordError = true, passwordMsgError = "Password harus diisi")
            }
            valid =  true
        } else if(_uiState.value.password.length < 8) {
            _uiState.update {
                it.copy(passwordError = true, passwordMsgError = "Password minimal 8 karakter")
            }
            valid = true
        }

        return valid
    }

    private fun executeLogin(login: String, pass: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    loginError = false,
                    loginMsgError = "",
                    passwordError = false,
                    passwordMsgError = ""
                )
            }

            loginUseCase(login, pass).collect { result ->
                result.onSuccess {
                    _uiState.update { state -> state.copy(isLoading = false, kasir = it) }
                    _effect.send(LoginEffect.NavigateToHome)
                }.onFailure {
                    _uiState.update { state -> state.copy(isLoading = false, error = it.message) }
                    _effect.send(LoginEffect.ShowSnackbar(it.message!!))
                }
            }
        }
    }
}