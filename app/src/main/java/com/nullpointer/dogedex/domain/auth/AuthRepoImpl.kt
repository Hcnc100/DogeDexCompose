package com.nullpointer.dogedex.domain.auth

import com.nullpointer.dogedex.datasource.auth.local.AuthLocalDataSource
import com.nullpointer.dogedex.datasource.auth.remote.AuthRemoteDataSource
import com.nullpointer.dogedex.datasource.dogs.local.DogLocalDataSourceLocal
import com.nullpointer.dogedex.datasource.settings.local.SettingsLocalDataSource
import com.nullpointer.dogedex.models.auth.data.AuthData
import com.nullpointer.dogedex.models.auth.dto.SignInDTO
import com.nullpointer.dogedex.models.auth.dto.SignUpDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AuthRepoImpl(
    private val dogLocalDataSourceLocal: DogLocalDataSourceLocal,
    private val authLocalDataSource: AuthLocalDataSource,
    private val authRemoteDataSource: AuthRemoteDataSource,
    private val settingsLocalDataSource: SettingsLocalDataSource,
) : AuthRepository {
    override val currentUser: Flow<AuthData?> = authLocalDataSource.getAuthData()
    override val isAuthUser: Flow<Boolean> = currentUser.map { it != null }

    override suspend fun signIn(userCredentials: SignInDTO) {
        val authData = authRemoteDataSource.signIn(userCredentials)
        authLocalDataSource.updateAuthData(authData)
    }

    override suspend fun signUp(userCredentials: SignUpDTO) {
        val authData = authRemoteDataSource.signUp(userCredentials)
        authLocalDataSource.updateAuthData(authData)
    }

    override suspend fun signOut() {
        settingsLocalDataSource.clearDataSaved()
        authLocalDataSource.deleteAuthData()
        dogLocalDataSourceLocal.deleteAllDogs()
    }

}