package io.alramdhan.lanadi.data.repository

import io.alramdhan.lanadi.core.utils.DispatcherProvider
import io.alramdhan.lanadi.data.local.dao.CartDAO
import io.alramdhan.lanadi.data.mapper.toCartEntity
import io.alramdhan.lanadi.data.mapper.toDomain
import io.alramdhan.lanadi.domain.models.CartProduk
import io.alramdhan.lanadi.domain.repository.ICartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class CartRepositoryImpl(private val dao: CartDAO, private val dispatcher: DispatcherProvider) : ICartRepository {
    override fun getAllCartItems(): Flow<List<CartProduk>> =
        dao.getCarts().map { list ->
            list.map { it.toDomain() }
        }.flowOn(dispatcher.io)

    override suspend fun insertCartItem(product: CartProduk) {
        withContext(dispatcher.io) {
            dao.insert(product.toCartEntity())
        }
    }
}