package io.alramdhan.lanadi.data.repository

import io.alramdhan.lanadi.data.mapper.toDomain
import io.alramdhan.lanadi.data.remote.ApiService
import io.alramdhan.lanadi.domain.models.Kategori
import io.alramdhan.lanadi.domain.repository.IProdukRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ProdukRepositoryImpl(private val apiService: ApiService): IProdukRepository, BaseRepository() {
    override suspend fun getKategoris(): Flow<Result<List<Kategori>>> = flow {
        val result = safeApiCall { apiService.getKategori() }

        result.fold(
            onSuccess = { response ->
                val categories = response.data!!.kategori.map { it.toDomain() }
                emit(Result.success(categories))
            },
            onFailure = {
                emit(Result.failure(Exception(it.localizedMessage)))
            }
        )
    }
}