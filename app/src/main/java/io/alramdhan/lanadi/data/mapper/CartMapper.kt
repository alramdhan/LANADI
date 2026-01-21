package io.alramdhan.lanadi.data.mapper

import io.alramdhan.lanadi.data.local.entity.CartEntity
import io.alramdhan.lanadi.domain.models.CartProduk

fun CartEntity.toDomain() = CartProduk(
    id = this.productId,
    name = this.name,
    price = this.price,
    quantity = this.quantity,
    imageUrl = this.imageUrl
)

fun CartProduk.toCartEntity() = CartEntity(
    productId = this.id,
    name = this.name,
    price = this.price,
    quantity = this.quantity,
    imageUrl = this.imageUrl
)