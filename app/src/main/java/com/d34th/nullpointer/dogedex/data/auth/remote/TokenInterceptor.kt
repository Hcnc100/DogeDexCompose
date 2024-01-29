package com.d34th.nullpointer.dogedex.data.auth.remote

import com.d34th.nullpointer.dogedex.datasource.auth.local.AuthLocalDataSource
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class TokenInterceptor(
    private val authLocalDataSource: AuthLocalDataSource,
) : Interceptor {

    private fun Request.signWithToken(token: String) =
        newBuilder()
            .header("AUTH-TOKEN", token)
            .header("Accept", "application/json")
            .build()

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val token = runBlocking { authLocalDataSource.getAuthData().first()?.token }
        return when {
            token != null -> {
                val signedRequest = request.signWithToken(token)
                chain.proceed(signedRequest)
            }

            else -> chain.proceed(request)
        }
    }
}