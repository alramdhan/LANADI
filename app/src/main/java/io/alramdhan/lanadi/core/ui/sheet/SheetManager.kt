package io.alramdhan.lanadi.core.ui.sheet

import androidx.compose.runtime.Composable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

interface SheetManager {
    val sheetState: StateFlow<BottomSheetState?>
    fun showSheet(onDismiss: () -> Unit = {}, content: @Composable () -> Unit)
    fun dismissSheet()
}

class SheetManagerImpl: SheetManager {
    private val _sheetState = MutableStateFlow<BottomSheetState?>(null)
    override  val sheetState = _sheetState.asStateFlow()

    override fun showSheet(onDismiss: () -> Unit, content: @Composable () -> Unit) {
        _sheetState.value = BottomSheetState(content, onDismiss)
    }

    override fun dismissSheet() {
        _sheetState.value = null
    }

}