package io.alramdhan.lanadi.domain.usecase

import io.alramdhan.lanadi.domain.models.CartProduk
import io.alramdhan.lanadi.domain.repository.ICartRepository

class DeleteCartUseCase(private val repository: ICartRepository) {
    suspend operator fun invoke(item: CartProduk) = repository.deleteCart(item)
}