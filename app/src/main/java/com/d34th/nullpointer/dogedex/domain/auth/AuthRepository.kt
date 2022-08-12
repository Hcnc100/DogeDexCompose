package com.d34th.nullpointer.dogedex.domain.auth

import com.d34th.nullpointer.dogedex.models.ApiResponse
import com.d34th.nullpointer.dogedex.models.User
import com.d34th.nullpointer.dogedex.models.listDogsApi.UserFieldSignIn
import com.d34th.nullpointer.dogedex.models.listDogsApi.UserFieldSignUp
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val currentUser: Flow<User>
    val isAuthUser: Flow<Boolean>

    suspend fun signIn(userCredentials: UserFieldSignIn): ApiResponse<User>
    suspend fun signUp(userCredentials: UserFieldSignUp): ApiResponse<User>
    suspend fun signOut()
}