package io.alramdhan.lanadi.domain.models

data class Kasir(
    val id: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val image: String,
    val accessToken: String,
    val refreshToken: String
)

