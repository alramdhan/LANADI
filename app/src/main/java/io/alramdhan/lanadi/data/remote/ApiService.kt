package io.alramdhan.lanadi.data.remote

import io.alramdhan.lanadi.core.constants.URL
import io.alramdhan.lanadi.data.remote.dto.BaseResponse
import io.alramdhan.lanadi.data.remote.dto.GetKategoriResponse
import io.alramdhan.lanadi.data.remote.dto.LoginRequest
import io.alramdhan.lanadi.data.remote.dto.LoginResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST(URL.ENDPOINT_LOGIN)
    suspend fun login(@Body request: LoginRequest): BaseResponse<LoginResponse>

    @POST(URL.ENDPOINT_LOGOUT)
    suspend fun logout(): BaseResponse<Unit>

    @GET(URL.ENDPOINT_GET_KATEGORI)
    suspend fun getKategori(): BaseResponse<GetKategoriResponse>
}