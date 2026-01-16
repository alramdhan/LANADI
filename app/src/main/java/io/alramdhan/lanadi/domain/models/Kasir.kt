package io.alramdhan.lanadi.domain.models

data class Kasir(
    val id: String,
    val name: String,
    val email: String,
    val image: String? = null,
    val accessToken: String,
)

