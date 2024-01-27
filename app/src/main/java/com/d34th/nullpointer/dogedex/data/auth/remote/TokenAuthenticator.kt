package com.d34th.nullpointer.dogedex.data.auth.remote

import com.d34th.nullpointer.dogedex.datasource.auth.local.AuthLocalDataSource
import com.d34th.nullpointer.dogedex.datasource.auth.remote.AuthRemoteDataSource
import com.d34th.nullpointer.dogedex.models.auth.dto.SignInDTO
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request


import okhttp3.Response
import okhttp3.Route
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Provider

class TokenRefreshAuthenticator(
    private val authLocalDataSource: AuthLocalDataSource,
    private val authRemoteDataSourceProvider: Provider<AuthRemoteDataSource>
) : Authenticator {
    private val Response.retryCount: Int
        get() {
            var currentResponse = priorResponse
            var result = 0
            while (currentResponse != null) {
                result++
                currentResponse = currentResponse.priorResponse
            }
            return result
        }

    override fun authenticate(route: Route?, response: Response): Request? = runBlocking {
        when {
            response.retryCount > 5 -> null
            else -> response.createSignedRequest()
        }
    }

    private fun Request.signWithToken(token: String) =
        newBuilder()
            .header("Authorization", "Bearer $token")
            .header("Accept", "application/json")
            .build()

    private suspend fun Response.createSignedRequest(): Request? {
        return try {
            // ! TODO: fix this
            val credential = authLocalDataSource.getAuthData().first()
            val authData = authRemoteDataSourceProvider.get().signIn(
                SignInDTO(
                    password = "",
                    email = ""
                )
            )
            authLocalDataSource.updateToken(authData.token)
            return request.signWithToken(authData.token)
        } catch (e: Exception) {
            Timber.e("Error authenticating with refresh token: ${e.message}")
            return null
        }
    }
}