package io.alramdhan.lanadi.domain.usecase

import io.alramdhan.lanadi.domain.repository.IAuthRepository

class LoginUseCase(private val repositoryImpl: IAuthRepository) {
    suspend operator fun invoke(login: String, password: String) = repositoryImpl.login(login, password)
}