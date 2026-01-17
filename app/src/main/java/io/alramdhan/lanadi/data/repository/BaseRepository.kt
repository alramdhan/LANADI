package io.alramdhan.lanadi.data.repository

import okio.IOException
import org.json.JSONObject
import retrofit2.HttpException

abstract class BaseRepository {
    protected suspend fun <T> safeApiCall(apiCall: suspend () -> T): Result<T> {
        return try {
            val response = apiCall()
            Result.success(response)
        } catch(e: HttpException) {
            val errorMessage = parseLaravelError(e)
            Result.failure(Exception(errorMessage))
        } catch(e: IOException) {
            Result.failure(Exception("Tidak ada koneksi internet"))
        } catch(e: Exception) {
            Result.failure(Exception("Terjadi kesalahan: ${e.localizedMessage}"))
        }
    }

    private fun parseLaravelError(e: HttpException): String {
        return try {
            val errorBody = e.response()?.errorBody()?.string()
            if(errorBody != null) {
                val jsonObject = JSONObject(errorBody)
                jsonObject.optString("message", "Terjadi kesalahan pada server")
            } else {
                "Gagal memuat data (Kode: ${e.code()}"
            }
        } catch(e: Exception) {
            "Terjadi kesalahan parsing data"
        }
    }
}