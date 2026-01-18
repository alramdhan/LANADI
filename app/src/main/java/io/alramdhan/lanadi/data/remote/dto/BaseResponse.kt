package io.alramdhan.lanadi.data.remote.dto

data class BaseResponse<T : Any>(
    val statusCode: Int,
    val success: Boolean,
    val message: String,
    val data: T? = null,
)