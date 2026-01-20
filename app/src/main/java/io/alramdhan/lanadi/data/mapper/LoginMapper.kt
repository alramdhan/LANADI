package io.alramdhan.lanadi.data.mapper

import io.alramdhan.lanadi.data.remote.dto.response.LoginResponse
import io.alramdhan.lanadi.domain.models.CurrentUser

fun LoginResponse.toDomain(): CurrentUser {
    return CurrentUser(
        user = this.user,
        accessToken = this.accessToken
    )
}