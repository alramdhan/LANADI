package io.alramdhan.lanadi.core.ui.sheet

import androidx.compose.runtime.Composable

data class BottomSheetState(
    val content: @Composable () -> Unit = {},
    val onDismiss: () -> Unit = {}
)
