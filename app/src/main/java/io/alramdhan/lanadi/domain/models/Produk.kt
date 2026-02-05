package io.alramdhan.lanadi.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Produk(
    val id: Int,
    val kategoriId: Int,
    val namaProduk: String,
    val deskripsi: String,
    val sku: String,
    val image: String?,
    val harga: Float,
    val stok: Int,
)
