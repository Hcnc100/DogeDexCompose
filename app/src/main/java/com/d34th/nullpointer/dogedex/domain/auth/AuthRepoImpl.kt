package com.d34th.nullpointer.dogedex.domain.auth

import com.d34th.nullpointer.dogedex.data.local.prefereneces.PreferencesDataSource
import com.d34th.nullpointer.dogedex.data.remote.auth.AuthDataSource
import com.d34th.nullpointer.dogedex.models.User
import com.d34th.nullpointer.dogedex.models.dtos.SignInDTO
import com.d34th.nullpointer.dogedex.models.dtos.SignUpDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AuthRepoImpl(
    private val authDataSource: AuthDataSource,
    private val preferencesDataSource: PreferencesDataSource,
) : AuthRepository {
    override val currentUser: Flow<User> = preferencesDataSource.currentUser
    override val isAuthUser: Flow<Boolean> = currentUser.map { it.id != -1L }

    override suspend fun signIn(userCredentials: SignInDTO) {
        val userResponse = authDataSource.signIn(userCredentials)
        preferencesDataSource.updateCurrentUser(userResponse)
    }

    override suspend fun signUp(userCredentials: SignUpDTO) {
        val userResponse = authDataSource.signUp(userCredentials)
        preferencesDataSource.updateCurrentUser(userResponse)
    }

    override suspend fun signOut() = preferencesDataSource.clearDataSaved()

}