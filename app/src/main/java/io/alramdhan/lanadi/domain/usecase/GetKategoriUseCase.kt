package io.alramdhan.lanadi.domain.usecase

import io.alramdhan.lanadi.domain.models.Kategori
import io.alramdhan.lanadi.domain.repository.IProdukRepository
import kotlinx.coroutines.flow.Flow

class GetKategoriUseCase(private val produkRepository: IProdukRepository) {
    suspend operator fun invoke(): Flow<Result<List<Kategori>>> = produkRepository.getKategoris()
}