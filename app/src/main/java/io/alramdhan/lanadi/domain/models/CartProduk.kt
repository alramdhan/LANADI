package io.alramdhan.lanadi.domain.models

data class CartProduk(
    val id: Int,
    val name: String,
    val price: Double,
    val quantity: Int,
    val imageUrl: String?
) {
    val totalPrice: Double
        get() = price * quantity
}
