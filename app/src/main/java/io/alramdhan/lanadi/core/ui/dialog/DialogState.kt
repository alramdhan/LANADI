package io.alramdhan.lanadi.core.ui.dialog

data class DialogState(
    val isVisible: Boolean = false,
    val title: String = "",
    val message: String = "",
    val confirmText: String = "Ya",
    val dismissText: String = "Batal",
    val onConfirm: () -> Unit = {},
    val onDismiss: () -> Unit = {},

    val isBasicDialog: Boolean = false
)