package io.alramdhan.lanadi.viewmodels.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.alramdhan.lanadi.domain.usecase.GetKategoriUseCase
import io.alramdhan.lanadi.ui.home.HomeEffect
import io.alramdhan.lanadi.ui.home.HomeIntent
import io.alramdhan.lanadi.ui.home.HomeState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(private val getKategoriUseCase: GetKategoriUseCase): ViewModel() {
    private val _uiState = MutableStateFlow(HomeState())
    val uiState = _uiState.asStateFlow()

    private val _effect = Channel<HomeEffect>()
    val effect = _effect.receiveAsFlow()

    init {
        onIntent(HomeIntent.LoadCategories)
    }

    fun onIntent(intent: HomeIntent) {
        when(intent) {
            is HomeIntent.LoadCategories -> fetchKategori()
            is HomeIntent.OnSelectKategori -> _uiState.update { it.copy(selectedKategori = intent.id) }
        }
    }

    private fun fetchKategori() {
        viewModelScope.launch {
            _uiState.update { it.copy(isKategoriLoading = true) }
            delay(500)
            getKategoriUseCase().collect { result ->
                result.onSuccess { response ->
                    _uiState.update { it.copy(isKategoriLoading = false, kategoris = response, selectedKategori = response[0].id) }
                }.onFailure { response ->
                    _uiState.update { it.copy(isKategoriLoading = false, errorKategori = response.localizedMessage) }
                    _effect.send(HomeEffect.ShowSnackBar(response.localizedMessage!!))
                }
            }
        }
    }
}