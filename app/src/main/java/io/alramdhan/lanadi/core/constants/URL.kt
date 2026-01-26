package io.alramdhan.lanadi.core.constants

object URL {
    const val BASE_URL: String = "http://192.168.1.9:8000/"

    data object ENDPOINT {
        const val LOGIN: String = "api/login"
        const val LOGOUT: String = "api/logout"
        const val GET_KATEGORI: String = "api/kategori/get"
        const val GET_PRODUK: String = "api/produk/get-all"

        const val POST_PESANAN: String = "api/order/store"
    }
}