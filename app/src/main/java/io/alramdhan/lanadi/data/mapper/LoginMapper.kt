package io.alramdhan.lanadi.data.mapper

import io.alramdhan.lanadi.data.remote.dto.LoginResponse
import io.alramdhan.lanadi.domain.models.Kasir

fun LoginResponse.toDomain(): Kasir {
    return Kasir(
        id = this.id,
        name = this.name,
        email = this.email,
        accessToken = this.accessToken ?: "",
    )
}