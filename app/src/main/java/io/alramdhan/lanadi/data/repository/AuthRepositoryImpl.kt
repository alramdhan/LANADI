package io.alramdhan.lanadi.data.repository

import io.alramdhan.lanadi.core.constants.Prefs
import io.alramdhan.lanadi.data.local.TokenManager
import io.alramdhan.lanadi.data.mapper.toDomain
import io.alramdhan.lanadi.data.remote.dto.LoginRequest
import io.alramdhan.lanadi.data.remote.ApiService
import io.alramdhan.lanadi.domain.models.CurrentUser
import io.alramdhan.lanadi.domain.repository.IAuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AuthRepositoryImpl(
    private val apiService: ApiService,
    private val tokenManager: TokenManager
): IAuthRepository, BaseRepository() {
    override suspend fun login(login: String, password: String): Flow<Result<CurrentUser>> = flow {
        val result = safeApiCall {
            apiService.login(LoginRequest(login, password))
        }

        result.fold(
            onSuccess = { response ->
                tokenManager.saveToken(response.data!!.accessToken!!)
                emit(Result.success(response.data.toDomain()))
            },
            onFailure = { response ->
                emit(Result.failure(Exception(response.message)))
            }
        )
    }

    override suspend fun logout(): Flow<Result<String?>> = flow {
        val result = safeApiCall { apiService.logout() }

        result.fold(
            onSuccess = { response ->
                tokenManager.clearToken()
                emit(Result.success(response.message))
            },
            onFailure = { response ->
                emit(Result.failure(Exception(response.localizedMessage)))
            }
        )
    }
}