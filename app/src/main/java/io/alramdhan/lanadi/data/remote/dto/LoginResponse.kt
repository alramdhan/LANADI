package io.alramdhan.lanadi.data.remote.dto

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    val id: String,
    val name: String,
    val email: String,
    val image: String? = null,
    @SerializedName("access_token")
    val accessToken: String?,
)
