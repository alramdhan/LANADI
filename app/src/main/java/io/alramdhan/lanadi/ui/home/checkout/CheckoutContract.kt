package io.alramdhan.lanadi.ui.home.checkout

import io.alramdhan.lanadi.domain.models.CartProduk
import io.alramdhan.lanadi.domain.models.Order

sealed class CheckoutEffect {

}

sealed class CheckoutIntent {
    data class PostOrder(val request: Order): CheckoutIntent()
}

data class CheckoutState(
    val products: List<CartProduk> = emptyList(),
    val namaPelanggan: String = "",
    val totalPrice: Double = 0.0
)