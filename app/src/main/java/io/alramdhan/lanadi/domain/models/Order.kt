package io.alramdhan.lanadi.domain.models

data class Order(
    val items: List<CartProduk>,
    val payAmount: Double,
    val paymentMethod: String = "cash",
    val namaPelanggan: String
)
