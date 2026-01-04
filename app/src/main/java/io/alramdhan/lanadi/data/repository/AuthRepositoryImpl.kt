package io.alramdhan.lanadi.data.repository

import io.alramdhan.lanadi.data.dto.LoginRequest
import io.alramdhan.lanadi.data.remote.ApiService
import io.alramdhan.lanadi.domain.models.Kasir
import io.alramdhan.lanadi.domain.repository.IAuthRepository

class AuthRepositoryImpl(private val apiService: ApiService): IAuthRepository {
    override suspend fun login(email: String, password: String): Result<Kasir> {
        return try {
            val response = apiService.login(LoginRequest(email, password))
            if (response.success && response.data != null) {
                Result.success(Kasir(response.data.id, response.data.email, response.data.token))
            } else {
                Result.failure(Exception(response.message))
            }
        } catch(e: Exception) {
            Result.failure(e)
        }
    }
}