package io.alramdhan.lanadi.domain.repository

import io.alramdhan.lanadi.domain.models.Kategori
import io.alramdhan.lanadi.domain.models.Produk
import kotlinx.coroutines.flow.Flow

interface IProdukRepository {
    suspend fun getKategoris(): Flow<Result<List<Kategori>>>
    suspend fun getProduks(): Flow<Result<List<Produk>>>
}