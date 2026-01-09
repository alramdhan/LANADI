package io.alramdhan.lanadi.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ApiResponseDTO<T>(
    val success: Boolean,
    val message: String,
    val data: T?,
    val accessToken: String?
)

data class UserDataDTO(
    val id: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val image: String,
    @SerializedName("access_token")
    val accessToken: String?,
    @SerializedName("refresh_token")
    val refreshToken: String?
)