package com.d34th.nullpointer.dogedex.data.remote.auth

import com.d34th.nullpointer.dogedex.models.User
import com.d34th.nullpointer.dogedex.models.dtos.SignInDTO
import com.d34th.nullpointer.dogedex.models.dtos.SignUpDTO

interface AuthDataSource {
    suspend fun signUp(userCredentials: SignUpDTO): User
    suspend fun signIn(userCredentials: SignInDTO): User
}