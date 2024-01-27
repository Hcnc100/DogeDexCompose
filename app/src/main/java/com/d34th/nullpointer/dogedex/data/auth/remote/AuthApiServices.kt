package com.d34th.nullpointer.dogedex.data.auth.remote

import com.d34th.nullpointer.dogedex.models.auth.dto.SignInDTO
import com.d34th.nullpointer.dogedex.models.authDogApiResponse.auth.AuthResponse
import com.d34th.nullpointer.dogedex.models.auth.dto.SignUpDTO
import retrofit2.http.Body
import retrofit2.http.POST


interface AuthApiServices{
    companion object{
        private const val SIGN_UP_PATH = "sign_up"
        private const val SIGN_IN_PATH = "sign_in"
    }

    @POST(SIGN_UP_PATH)
    suspend fun signUp(
        @Body data: SignUpDTO
    ): AuthResponse

    @POST(SIGN_IN_PATH)
    suspend fun signIn(
        @Body data: SignInDTO
    ): AuthResponse
}