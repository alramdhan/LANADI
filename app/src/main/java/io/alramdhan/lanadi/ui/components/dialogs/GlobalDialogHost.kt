package io.alramdhan.lanadi.ui.components.dialogs

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.window.Dialog
import io.alramdhan.lanadi.core.ui.dialog.DialogManager
import io.alramdhan.lanadi.core.ui.loading.LoadingManager
import io.alramdhan.lanadi.ui.theme.Danger
import org.koin.compose.koinInject

@Composable
fun GlobalDialogHost(
    dialogManager: DialogManager = koinInject(),
    loadingManager: LoadingManager = koinInject()
) {
    val state by dialogManager.dialogState.collectAsState()
    val loadingState by loadingManager.loadingState.collectAsState()

    if(state.isVisible) {
        AlertDialog(
            onDismissRequest = { state.onDismiss() },
            title = {
                Text(state.title)
            },
            text = {
                Text(state.message)
            },
            confirmButton = {
                if(state.showConfirmButton) {
                    TextButton(onClick = state.onConfirm) {
                        Text(state.confirmText,
                            color = Danger
                        )
                    }
                }
            },
            dismissButton = {
                TextButton(onClick = state.onDismiss) {
                    Text(state.dismissText,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = .6f)
                    )
                }
            }
        )
    }

    if(state.isBasicDialog) {
        AlertDialog(
            onDismissRequest = { state.onDismiss() },
            title = { Text("Perhatian") },
            text = { Text(state.message) },
            confirmButton = {
                TextButton(state.onConfirm) {
                    Text(state.confirmText)
                }
            }
        )
    }

    if(loadingState.isVisible) {
        Dialog(
            onDismissRequest = { state.onDismiss() },
            content = {
                CircularProgressIndicator()
            }
        )
    }
}