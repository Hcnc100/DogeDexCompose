package com.d34th.nullpointer.dogedex.domain.auth

import com.d34th.nullpointer.dogedex.models.ApiResponse
import com.d34th.nullpointer.dogedex.models.User
import com.d34th.nullpointer.dogedex.models.dtos.SignInDTO
import com.d34th.nullpointer.dogedex.models.dtos.SignUpDTO
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val currentUser: Flow<User>
    val isAuthUser: Flow<Boolean>

    suspend fun signIn(userCredentials: SignInDTO): ApiResponse<User>
    suspend fun signUp(userCredentials: SignUpDTO): ApiResponse<User>
    suspend fun signOut()
}