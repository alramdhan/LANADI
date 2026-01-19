package io.alramdhan.lanadi.data.remote

import io.alramdhan.lanadi.data.local.TokenManager
import io.alramdhan.lanadi.viewmodels.auth.AuthManager
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val tokenManager: TokenManager, private val authManager: AuthManager): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder()

        requestBuilder.addHeader("Accept", "application/json")

        val token = tokenManager.getToken()
        if(!token.isNullOrEmpty()) {
            requestBuilder.addHeader("Authorization", "Bearer $token")
        }

        val response = chain.proceed(requestBuilder.build())
        if(response.code == 401) {
            runBlocking {
                tokenManager.clearToken()
                authManager.logout()
            }
        }

        return response
    }
}