package com.nullpointer.dogedex.datasource.auth.remote

import com.nullpointer.dogedex.models.auth.data.AuthData
import com.nullpointer.dogedex.models.auth.dto.SignInDTO
import com.nullpointer.dogedex.models.auth.dto.SignUpDTO

interface AuthRemoteDataSource {
    suspend fun signUp(userCredentials: SignUpDTO): AuthData
    suspend fun signIn(userCredentials: SignInDTO): AuthData
}