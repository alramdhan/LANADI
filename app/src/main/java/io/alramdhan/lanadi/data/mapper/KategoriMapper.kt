package io.alramdhan.lanadi.data.mapper

import io.alramdhan.lanadi.data.remote.dto.response.ItemKategori
import io.alramdhan.lanadi.domain.models.Kategori

fun ItemKategori.toDomain(): Kategori {
    return Kategori(
        id = this.id,
        name = this.name,
        image = this.image,
        sortOrder = this.sortOrder
    )
}