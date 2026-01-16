package io.alramdhan.lanadi.data.remote

import io.alramdhan.lanadi.data.remote.dto.ApiResponseDTO
import io.alramdhan.lanadi.data.remote.dto.LoginRequest
import io.alramdhan.lanadi.domain.models.Kasir
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {
    @Headers("Content-Type: application/json")
    @POST("api/login")
    suspend fun login(@Body request: LoginRequest): ApiResponseDTO<Kasir>
}