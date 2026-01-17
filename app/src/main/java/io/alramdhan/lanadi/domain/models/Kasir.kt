package io.alramdhan.lanadi.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class CurrentUser(
    val user: Kasir,
    val accessToken: String?
)

@Serializable
data class Kasir(
    val id: String,
    val name: String,
    val username: String?,
    val email: String,
    val image: String? = null,
)

