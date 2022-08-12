package com.d34th.nullpointer.dogedex.data.remote.auth

import com.d34th.nullpointer.dogedex.models.ApiResponse
import com.d34th.nullpointer.dogedex.models.User
import com.d34th.nullpointer.dogedex.models.listDogsApi.UserFieldSignIn
import com.d34th.nullpointer.dogedex.models.listDogsApi.UserFieldSignUp

interface AuthDataSource {
    suspend fun signUp(userCredentials: UserFieldSignUp): ApiResponse<User>
    suspend fun signIn(userCredentials: UserFieldSignIn): ApiResponse<User>
}