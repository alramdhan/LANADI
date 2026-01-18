package io.alramdhan.lanadi.viewmodels.home.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.alramdhan.lanadi.domain.usecase.LogoutUseCase
import io.alramdhan.lanadi.ui.home.setting.SettingEffect
import io.alramdhan.lanadi.ui.home.setting.SettingIntent
import io.alramdhan.lanadi.ui.home.setting.SettingState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class SettingViewModel(private val logOutUseCase: LogoutUseCase): ViewModel() {
    private val _uiState = MutableStateFlow(SettingState(""))
    val uiState = _uiState.asStateFlow()

    private val _effect = Channel<SettingEffect>()
    val effect = _effect.receiveAsFlow()

    fun onIntent(intent: SettingIntent) {
        when(intent) {
            is SettingIntent.LogoutClicked -> executeLogout()
        }
    }

    private fun executeLogout() {
        viewModelScope.launch {
            logOutUseCase().collect { result ->
                result.onSuccess {
                    _effect.send(SettingEffect.NavigateToLogin)
                }.onFailure { response ->
                    _effect.send(SettingEffect.ShowSnackBar(response.localizedMessage!!))
                }
            }
        }
    }
}