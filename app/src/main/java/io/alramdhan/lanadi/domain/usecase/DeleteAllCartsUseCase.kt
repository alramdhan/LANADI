package io.alramdhan.lanadi.domain.usecase

import io.alramdhan.lanadi.domain.repository.ICartRepository

class DeleteAllCartsUseCase(private val repository: ICartRepository) {
    suspend operator fun invoke() = repository.deleteAllCarts()
}