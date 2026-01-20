package io.alramdhan.lanadi.data.remote.dto.response

import com.google.gson.annotations.SerializedName

data class GetProdukResponse(
    val produk: List<ItemProduk>
)

data class ItemProduk(
    val id: Int,
    @SerializedName("kategori_id")
    val kategoriId: Int,
    @SerializedName("nama_produk")
    val namaProduk: String,
    val deskripsi: String,
    val sku: String,
    val image: String?,
    val harga: Float,
    val stok: Int,
)
