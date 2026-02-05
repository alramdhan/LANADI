package io.alramdhan.lanadi.data.repository

import io.alramdhan.lanadi.data.mapper.toRemoteDTO
import io.alramdhan.lanadi.data.remote.ApiService
import io.alramdhan.lanadi.domain.models.Order
import io.alramdhan.lanadi.domain.models.Transaction
import io.alramdhan.lanadi.domain.repository.IOrderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class OrderRepositoryImpl(private val apiService: ApiService): IOrderRepository, BaseRepository() {
    override suspend fun makeAnOrder(request: Order): Flow<Result<Transaction>> = flow {
        val result = safeApiCall {
            apiService.makeAnOrder(request.toRemoteDTO())
        }

        result.fold(
            onSuccess = {},
            onFailure = {}
        )
    }
}