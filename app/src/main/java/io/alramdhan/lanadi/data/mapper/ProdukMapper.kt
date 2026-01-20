package io.alramdhan.lanadi.data.mapper

import io.alramdhan.lanadi.data.remote.dto.response.ItemProduk
import io.alramdhan.lanadi.domain.models.Produk

fun ItemProduk.toDomain(): Produk {
    return Produk(
        id = this.id,
        kategoriId = this.kategoriId,
        namaProduk = this.namaProduk,
        deskripsi = this.deskripsi,
        sku = this.sku,
        image = this.image,
        harga = this.harga,
        stok = this.stok,
    )
}