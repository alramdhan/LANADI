package io.alramdhan.lanadi.domain.usecase

import io.alramdhan.lanadi.domain.repository.ICartRepository

class GetCartUseCase(private val repository: ICartRepository) {
    operator fun invoke() = repository.getAllCartItems()
}