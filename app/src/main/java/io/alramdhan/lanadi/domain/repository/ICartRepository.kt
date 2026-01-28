package io.alramdhan.lanadi.domain.repository

import io.alramdhan.lanadi.domain.models.CartProduk
import kotlinx.coroutines.flow.Flow

interface ICartRepository {
    fun getAllCartItems(): Flow<List<CartProduk>>
    suspend fun insertCartItem(product: CartProduk)
    suspend fun updateCartQty(productId: Int, quantity: Int)
    suspend fun deleteCart(product: CartProduk)
    suspend fun deleteAllCarts()
}