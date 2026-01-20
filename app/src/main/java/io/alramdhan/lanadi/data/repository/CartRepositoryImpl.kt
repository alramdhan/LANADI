package io.alramdhan.lanadi.data.repository

import io.alramdhan.lanadi.data.local.dao.CartDAO
import io.alramdhan.lanadi.data.local.entity.CartEntity
import io.alramdhan.lanadi.domain.models.CartProduk
import io.alramdhan.lanadi.domain.repository.ICartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CartRepositoryImpl(private val dao: CartDAO) : ICartRepository {
    override fun getAllCartItems(): Flow<List<CartProduk>> =
        dao.getCarts().map { list ->
            list.map {
                CartProduk(
                    id = it.productId,
                    name = it.name,
                    price = it.price,
                    quantity = it.quantity,
                    imageUrl = it.imageUrl
                )
            }
        }

    override suspend fun insertCartItem(product: CartProduk) {
        dao.insert(
            CartEntity(
                productId = product.id,
                name = product.name,
                price = product.price,
                quantity = product.quantity,
                imageUrl = product.imageUrl
            )
        )
    }
}