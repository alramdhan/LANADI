package io.alramdhan.lanadi.domain.usecase

import io.alramdhan.lanadi.domain.models.Order
import io.alramdhan.lanadi.domain.models.Transaction
import io.alramdhan.lanadi.domain.repository.IOrderRepository
import kotlinx.coroutines.flow.Flow

class MakeAnOrderUseCase(private val repository: IOrderRepository) {
    suspend operator fun invoke(request: Order): Flow<Result<Transaction>> = repository.makeAnOrder(request)
}