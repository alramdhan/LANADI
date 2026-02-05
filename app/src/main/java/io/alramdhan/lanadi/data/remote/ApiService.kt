package io.alramdhan.lanadi.data.remote

import io.alramdhan.lanadi.core.constants.URL
import io.alramdhan.lanadi.data.remote.dto.BaseResponse
import io.alramdhan.lanadi.data.remote.dto.response.GetKategoriResponse
import io.alramdhan.lanadi.data.remote.dto.response.GetProdukResponse
import io.alramdhan.lanadi.data.remote.dto.request.LoginRequest
import io.alramdhan.lanadi.data.remote.dto.request.OrderStoreRequest
import io.alramdhan.lanadi.data.remote.dto.response.LoginResponse
import io.alramdhan.lanadi.data.remote.dto.response.OrderResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST(URL.ENDPOINT.LOGIN)
    suspend fun login(@Body request: LoginRequest): BaseResponse<LoginResponse>

    @POST(URL.ENDPOINT.LOGOUT)
    suspend fun logout(): BaseResponse<Unit>

    @GET(URL.ENDPOINT.GET_KATEGORI)
    suspend fun getKategori(): BaseResponse<GetKategoriResponse>

    @GET(URL.ENDPOINT.GET_PRODUK)
    suspend fun getProduk(): BaseResponse<GetProdukResponse>

    @POST(URL.ENDPOINT.POST_PESANAN)
    suspend fun makeAnOrder(@Body request: OrderStoreRequest): BaseResponse<OrderResponse>
}