package io.alramdhan.lanadi.domain.repository

import io.alramdhan.lanadi.domain.models.Order
import io.alramdhan.lanadi.domain.models.Transaction
import kotlinx.coroutines.flow.Flow

interface IOrderRepository {
    suspend fun makeAnOrder(request: Order): Flow<Result<Transaction>>
}