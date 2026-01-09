package io.alramdhan.lanadi.data.mapper

import io.alramdhan.lanadi.data.remote.dto.ApiResponseDTO
import io.alramdhan.lanadi.data.remote.dto.UserDataDTO
import io.alramdhan.lanadi.domain.models.Kasir

fun UserDataDTO.toDomain(): Kasir {
    return Kasir(
        id = this.id,
        email = this.email,
        firstName = this.firstName,
        lastName = this.lastName,
        image = this.image,
        accessToken = this.accessToken ?: "",
        refreshToken = this.refreshToken ?: ""
    )
}