package io.alramdhan.lanadi.core.ui.dialog

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DialogManager {
    private val _dialogState = MutableStateFlow(DialogState())
    val dialogState = _dialogState.asStateFlow()

    fun basicDialog(message: String) {
        _dialogState.update {
            it.copy(isBasicDialog = true, message = message, onConfirm = { dismiss() })
        }
    }

    fun show(
        title: String,
        message: String,
        confirmText: String = "Ya",
        dismissText: String = "Batal",
        onConfirm: () -> Unit,
        onDismiss: () -> Unit = { dismiss() }
    ) {
        _dialogState.update {
            it.copy(
                isVisible = true,
                title = title,
                message = message,
                confirmText = confirmText,
                dismissText = dismissText,
                onConfirm = {
                    onConfirm()
                    dismiss()
                },
                onDismiss = {
                    onDismiss()
                    dismiss()
                }
            )
        }
    }

    private fun dismiss() {
        _dialogState.update { it.copy(isVisible = false, isBasicDialog = false) }
    }
}