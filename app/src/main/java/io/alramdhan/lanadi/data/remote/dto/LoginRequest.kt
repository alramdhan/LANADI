package io.alramdhan.lanadi.data.remote.dto

import com.google.gson.annotations.SerializedName

data class LoginRequest(
//    @SerializedName("login")
//    val login: String,
    @SerializedName("username")
    val username: String,
    @SerializedName("password")
    val password: String
)
