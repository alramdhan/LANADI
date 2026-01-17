package io.alramdhan.lanadi.data.remote

import io.alramdhan.lanadi.core.constants.URL
import io.alramdhan.lanadi.data.remote.dto.ApiResponseDTO
import io.alramdhan.lanadi.data.remote.dto.LoginRequest
import io.alramdhan.lanadi.data.remote.dto.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST(URL.ENDPOINT_LOGIN)
    suspend fun login(@Body request: LoginRequest): ApiResponseDTO<LoginResponse>

    @POST(URL.ENDPOINT_LOGOUT)
    suspend fun logout(): ApiResponseDTO<Unit>
}