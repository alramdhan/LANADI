package io.alramdhan.lanadi.domain.usecase

import io.alramdhan.lanadi.domain.repository.IAuthRepository
import kotlinx.coroutines.flow.Flow

class LogoutUseCase(private val authRepository: IAuthRepository) {
    suspend operator fun invoke(): Flow<Result<String?>> = authRepository.logout()
}