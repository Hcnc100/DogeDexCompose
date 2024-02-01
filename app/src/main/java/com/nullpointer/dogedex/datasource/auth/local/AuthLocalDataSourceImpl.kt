package com.nullpointer.dogedex.datasource.auth.local

import com.nullpointer.dogedex.data.auth.local.AuthDataStore
import com.nullpointer.dogedex.models.auth.data.AuthData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class AuthLocalDataSourceImpl(
    private val authDataStore: AuthDataStore
):AuthLocalDataSource {
    override suspend fun updateAuthData(authData: AuthData) =
        authDataStore.updateAuthData(authData)

    override fun getAuthData(): Flow<AuthData?> =
        authDataStore.getAuthData()

    override suspend fun deleteAuthData() =
        authDataStore.clearAuthData()

    override suspend fun updateToken(token: String) {
        val authData = (authDataStore.getAuthData().first() ?: AuthData()).copy(token = token)
        authDataStore.updateAuthData(authData)
    }
}