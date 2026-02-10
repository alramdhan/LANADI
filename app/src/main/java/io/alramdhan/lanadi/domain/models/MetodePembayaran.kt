package io.alramdhan.lanadi.domain.models

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.QrCode2
import androidx.compose.ui.graphics.vector.ImageVector

enum class MetodePembayaran(val label: String, val icon: ImageVector) {
    QRIS("QRIS", Icons.Default.QrCode2),
    CASH("TUNAI / CASH", Icons.Default.Payments),
    TRANSFER("TRANSFER", Icons.Default.CreditCard)
}