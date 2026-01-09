package io.alramdhan.lanadi.domain.repository

import io.alramdhan.lanadi.domain.models.Kasir

interface IAuthRepository {
    suspend fun login(login: String, password: String): Result<Kasir>
}