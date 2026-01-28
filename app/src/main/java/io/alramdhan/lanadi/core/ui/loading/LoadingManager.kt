package io.alramdhan.lanadi.core.ui.loading

import io.alramdhan.lanadi.core.ui.dialog.DialogState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoadingManager {
    private val _loadingState = MutableStateFlow(DialogState())
    val loadingState = _loadingState.asStateFlow()

    fun show() {
        _loadingState.update {
            it.copy(isVisible = true)
        }
    }

    private fun dismiss() {
        _loadingState.update { it.copy(isVisible = false) }
    }
}