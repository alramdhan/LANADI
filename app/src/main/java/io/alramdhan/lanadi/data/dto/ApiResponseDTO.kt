package io.alramdhan.lanadi.data.dto

data class ApiResponseDTO<T>(
    val success: Boolean,
    val message: String,
    val data: T?,
    val accessToken: String?
)
