package io.alramdhan.lanadi.domain.usecase

import io.alramdhan.lanadi.domain.models.Produk
import io.alramdhan.lanadi.domain.repository.IProdukRepository
import kotlinx.coroutines.flow.Flow

class GetProdukUseCase(private val repository: IProdukRepository) {
    suspend operator fun invoke(): Flow<Result<List<Produk>>> = repository.getProduks()
}