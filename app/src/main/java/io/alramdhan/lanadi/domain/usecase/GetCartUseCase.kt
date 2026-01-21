package io.alramdhan.lanadi.domain.usecase

import io.alramdhan.lanadi.domain.models.CartProduk
import io.alramdhan.lanadi.domain.repository.ICartRepository
import kotlinx.coroutines.flow.Flow

class GetCartUseCase(private val repository: ICartRepository) {
    operator fun invoke(): Flow<List<CartProduk>> = repository.getAllCartItems()
}