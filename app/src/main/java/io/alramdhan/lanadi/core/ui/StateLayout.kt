package io.alramdhan.lanadi.core.ui

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.alramdhan.lanadi.ui.components.LoadingView

sealed interface UiState<out T> {
    data object Idle: UiState<Nothing>
    data object Loading: UiState<Nothing>
    data object Empty: UiState<Nothing>
    data class Success<T>(val data: T): UiState<T>
    data class Error(val message: String): UiState<Nothing>
}

@Composable
fun <T> StateLayout(
    modifier: Modifier = Modifier,
    state: UiState<T>,
    onRetry: (() -> Unit)? = null,
    loadingContent: @Composable () -> Unit = { LoadingView() },
    errorContent: @Composable (String) -> Unit = { msg -> DefaultErrorView(msg, onRetry) },
    emptyContent: @Composable () -> Unit = { DefaultEmptyView() },
    content: @Composable (T) -> Unit
) {
    Box(modifier) {
        Crossfade(targetState = state, label = "State Animation", animationSpec = tween(500)) { target ->
            when(target) {
                is UiState.Idle -> {}
                is UiState.Loading -> {
                    loadingContent()
                }
                is UiState.Error -> {
                    errorContent(target.message)
                }
                is UiState.Empty -> {
                    emptyContent()
                }
                is UiState.Success -> {
                    content(target.data)
                }
            }
        }
    }
}