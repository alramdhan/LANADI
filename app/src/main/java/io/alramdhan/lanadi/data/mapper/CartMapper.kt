package io.alramdhan.lanadi.data.mapper

import io.alramdhan.lanadi.data.local.entity.CartEntity
import io.alramdhan.lanadi.domain.models.CartProduk

fun CartEntity.toDomain() = CartProduk(
    productId = this.productId,
    deskripsi = this.deskripsi,
    name = this.name,
    price = this.price,
    quantity = this.quantity,
    imageUrl = this.imageUrl
)

fun CartProduk.toCartEntity() = CartEntity(
    productId = this.productId,
    name = this.name,
    deskripsi = this.deskripsi,
    price = this.price,
    quantity = this.quantity,
    imageUrl = this.imageUrl
)