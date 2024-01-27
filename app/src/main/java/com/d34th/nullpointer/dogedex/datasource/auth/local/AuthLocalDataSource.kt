package com.d34th.nullpointer.dogedex.datasource.auth.local

import com.d34th.nullpointer.dogedex.models.auth.data.AuthData
import kotlinx.coroutines.flow.Flow

interface AuthLocalDataSource {
    fun getAuthData(): Flow<AuthData?>

    suspend fun updateAuthData(authData: AuthData)

    suspend fun deleteAuthData()
    suspend fun updateToken(token: String)
}