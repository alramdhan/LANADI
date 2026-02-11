package io.alramdhan.lanadi.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.alramdhan.lanadi.core.ui.sheet.SheetManager
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GlobalBottomSheetWrapper(sheetManager: SheetManager = koinInject()) {
    val state by sheetManager.sheetState.collectAsStateWithLifecycle()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()

    state?.let { currentSheet ->
        ModalBottomSheet(
            onDismissRequest = {
                currentSheet.onDismiss()
                sheetManager.dismissSheet()
            },
            sheetState = sheetState,
            dragHandle = { BottomSheetDefaults.DragHandle() }
        ) {
            currentSheet.content()
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}