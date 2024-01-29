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
            .header("AUTH-TOKEN", token)
            .header("Accept", "application/json")
            .build()

    private suspend fun Response.createSignedRequest(): Request? {
        return try {
            // * get auth data from local data source
            val authData = authLocalDataSource.getAuthData().first()!!
            // * create sign in dto from auth data
            val signInDTO = SignInDTO.fromAuthData(authData)
            // * get new auth data from remote data source
            val newAuthData = authRemoteDataSourceProvider.get().signIn(signInDTO)
            // * update token in local data source
            authLocalDataSource.updateToken(newAuthData.token)
            // * return signed request
            return request.signWithToken(newAuthData.token)
        } catch (e: Exception) {
            Timber.e("Error authenticating with refresh token: ${e.message}")
            return null
        }
    }


}