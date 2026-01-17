package io.alramdhan.lanadi.domain.usecase

import io.alramdhan.lanadi.domain.models.CurrentUser
import io.alramdhan.lanadi.domain.repository.IAuthRepository
import kotlinx.coroutines.flow.Flow

class LoginUseCase(private val repositoryImpl: IAuthRepository) {
    suspend operator fun invoke(login: String, password: String): Flow<Result<CurrentUser>> {
        return repositoryImpl.login(login, password)
    }
}