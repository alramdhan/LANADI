package io.alramdhan.lanadi.domain.repository

import io.alramdhan.lanadi.domain.models.CurrentUser
import kotlinx.coroutines.flow.Flow

interface IAuthRepository {
    suspend fun login(login: String, password: String): Flow<Result<CurrentUser>>
    suspend fun logout(): Flow<Result<String?>>
}