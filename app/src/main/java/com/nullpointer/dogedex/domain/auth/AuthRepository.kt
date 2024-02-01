package com.nullpointer.dogedex.domain.auth

import com.nullpointer.dogedex.models.auth.data.AuthData
import com.nullpointer.dogedex.models.auth.dto.SignInDTO
import com.nullpointer.dogedex.models.auth.dto.SignUpDTO
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val currentUser: Flow<AuthData?>
    val isAuthUser: Flow<Boolean>

    suspend fun signIn(userCredentials: SignInDTO)
    suspend fun signUp(userCredentials: SignUpDTO)
    suspend fun signOut()
}