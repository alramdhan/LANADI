package io.alramdhan.lanadi.viewmodels.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.alramdhan.lanadi.core.ui.UiState
import io.alramdhan.lanadi.domain.usecase.GetKategoriUseCase
import io.alramdhan.lanadi.domain.usecase.GetProdukUseCase
import io.alramdhan.lanadi.ui.home.HomeEffect
import io.alramdhan.lanadi.ui.home.HomeIntent
import io.alramdhan.lanadi.ui.home.HomeState
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getKategoriUseCase: GetKategoriUseCase,
    private val getProdukUseCase: GetProdukUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow(HomeState())
    val uiState = _uiState.asStateFlow()

    private val _effect = Channel<HomeEffect>()
    val effect = _effect.receiveAsFlow()

    init {
        onIntent(HomeIntent.LoadDataHome)
    }

    fun onIntent(intent: HomeIntent) {
        when(intent) {
            is HomeIntent.LoadDataHome -> fetchDataHome()
            is HomeIntent.OnSelectKategori -> _uiState.update { it.copy(selectedKategori = intent.id) }
            is HomeIntent.SearchTextChanged -> _uiState.update { it.copy(searchText = if(intent.text == "") null else intent.text) }
            is HomeIntent.ResetSearch -> _uiState.update { it.copy(searchText = null) }
        }
    }

    private fun fetchDataHome() {
        viewModelScope.launch {
            try {
                async { fetchKategori() }.await()
                async { fetchProduk() }.await()
            } catch(e: Exception) {
                _effect.send(HomeEffect.ShowToastMessage(e.localizedMessage ?: "Terjadi kesalahan"))
            }
        }
    }

    private fun fetchKategori() {
        viewModelScope.launch {
            _uiState.update { it.copy(kategoris = UiState.Loading) }
            delay(500)
            getKategoriUseCase().collect { result ->
                result.onSuccess { response ->
                    _uiState.update { it.copy(kategoris = UiState.Success(response)) }
                }.onFailure { response ->
                    _uiState.update { it.copy(kategoris = UiState.Error(response.localizedMessage!!)) }
                    _effect.send(HomeEffect.ShowToastMessage(response.localizedMessage ?: "Terjadi kesalahan"))
                }
            }
        }
    }

    private fun fetchProduk() {
        viewModelScope.launch {
            _uiState.update { it.copy(produks = UiState.Loading) }
            delay(1000)
            getProdukUseCase().collect { result ->
                result.onSuccess { data ->
                    _uiState.update { it.copy(produks = UiState.Success(data)) }
                }.onFailure { msg ->
                    _uiState.update { it.copy(produks = UiState.Error(msg.message!!)) }
                    _effect.send(HomeEffect.ShowToastMessage(msg.localizedMessage!!))
                }
            }
        }
    }
}