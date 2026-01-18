package io.alramdhan.lanadi.domain.repository

import io.alramdhan.lanadi.domain.models.Kategori
import kotlinx.coroutines.flow.Flow

interface IProdukRepository {
    suspend fun getKategoris(): Flow<Result<List<Kategori>>>
}