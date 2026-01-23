package io.alramdhan.lanadi.domain.usecase

import io.alramdhan.lanadi.domain.repository.ICartRepository

class UpdateCartQtyUseCase(private val repository: ICartRepository) {
    suspend operator fun invoke(productId: Int) = repository.updateCartQty(productId)
}