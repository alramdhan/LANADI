package io.alramdhan.lanadi.ui.home.checkout

import io.alramdhan.lanadi.domain.models.CartProduk
import io.alramdhan.lanadi.domain.models.MetodePembayaran
import io.alramdhan.lanadi.domain.models.Order

sealed class CheckoutEffect {
    data object NavigateToSuccess: CheckoutEffect()
    data class ShowError(val message: String): CheckoutEffect()
}

sealed class CheckoutIntent {
    data class SelectMethod(val method: MetodePembayaran): CheckoutIntent()
    data class ConfirmPayment(val request: Order): CheckoutIntent()
}

data class CheckoutState(
    val selectedMethod: MetodePembayaran? = MetodePembayaran.QRIS,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val products: List<CartProduk> = emptyList(),
    val namaPelanggan: String = "",
    val totalPrice: Double = 0.0
)