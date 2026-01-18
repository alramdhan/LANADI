package io.alramdhan.lanadi.domain.models

data class Kategori(
    val id: Int,
    val name: String,
    val image: String?,
    val sortOrder: Int?
)
