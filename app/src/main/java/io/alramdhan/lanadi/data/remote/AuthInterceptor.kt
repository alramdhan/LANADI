package io.alramdhan.lanadi.data.remote

import io.alramdhan.lanadi.data.local.TokenManager
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val tokenManager: TokenManager): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder()

        requestBuilder.addHeader("Accept", "application/json")

        val token = tokenManager.getToken()
        if(!token.isNullOrEmpty()) {
            requestBuilder.addHeader("Authorization", "Bearer $token")
        }

        val response = chain.proceed(requestBuilder.build())
        if(response.code == 404) {
            synchronized(this) {
                tokenManager.clearToken()
            }
        }

        return response
    }
}