package io.alramdhan.lanadi.data.remote.dto

import com.google.gson.annotations.SerializedName
import io.alramdhan.lanadi.domain.models.Kasir

data class LoginResponse(
    val user: Kasir,
    @SerializedName("access_token")
    val accessToken: String?
)
