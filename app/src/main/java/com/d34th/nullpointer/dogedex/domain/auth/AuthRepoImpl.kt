package com.d34th.nullpointer.dogedex.domain.auth

import com.d34th.nullpointer.dogedex.data.local.PrefsUser
import com.d34th.nullpointer.dogedex.data.remote.auth.AuthDataSource
import com.d34th.nullpointer.dogedex.models.ApiResponse
import com.d34th.nullpointer.dogedex.models.User
import com.d34th.nullpointer.dogedex.models.listDogsApi.UserFieldSignIn
import com.d34th.nullpointer.dogedex.models.listDogsApi.UserFieldSignUp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AuthRepoImpl(
    private val prefsUser: PrefsUser,
    private val authDataSource: AuthDataSource
) : AuthRepository {
    override val currentUser: Flow<User> = prefsUser.getUser()
    override val isAuthUser: Flow<Boolean> = currentUser.map { it.id != -1L }

    override suspend fun signIn(userCredentials: UserFieldSignIn): ApiResponse<User> {
        val userResponse = authDataSource.signIn(userCredentials)
        if (userResponse is ApiResponse.Success) prefsUser.changeUser(userResponse.response)
        return userResponse
    }

    override suspend fun signUp(userCredentials: UserFieldSignUp): ApiResponse<User> {
        val userResponse = authDataSource.signUp(userCredentials)
        if (userResponse is ApiResponse.Success) prefsUser.changeUser(userResponse.response)
        return userResponse
    }

    override suspend fun signOut() {

    }
}