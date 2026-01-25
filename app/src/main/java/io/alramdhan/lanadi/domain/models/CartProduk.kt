package io.alramdhan.lanadi.domain.models

data class CartProduk(
    val productId: Int,
    val name: String,
    val deskripsi: String,
    val price: Double,
    val quantity: Int,
    val imageUrl: String?
) {
    val totalPrice: Double
        get() = price * quantity
}
