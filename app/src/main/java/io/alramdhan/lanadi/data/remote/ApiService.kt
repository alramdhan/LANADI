package io.alramdhan.lanadi.data.remote

import io.alramdhan.lanadi.data.dto.ApiResponseDTO
import io.alramdhan.lanadi.data.dto.LoginRequest
import io.alramdhan.lanadi.domain.models.Kasir
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("api/login")
    suspend fun login(
        @Body request: LoginRequest
    ): ApiResponseDTO<Kasir>
}