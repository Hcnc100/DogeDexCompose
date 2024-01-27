package com.d34th.nullpointer.dogedex.datasource.auth.remote

import com.d34th.nullpointer.dogedex.models.User
import com.d34th.nullpointer.dogedex.models.auth.data.AuthData
import com.d34th.nullpointer.dogedex.models.auth.dto.SignInDTO
import com.d34th.nullpointer.dogedex.models.auth.dto.SignUpDTO

interface AuthRemoteDataSource {
    suspend fun signUp(userCredentials: SignUpDTO): AuthData
    suspend fun signIn(userCredentials: SignInDTO): AuthData
}