package com.nullpointer.dogedex.data.auth.remote

import com.nullpointer.dogedex.models.auth.dto.SignInDTO
import com.nullpointer.dogedex.models.auth.dto.SignUpDTO
import com.nullpointer.dogedex.models.auth.response.LoginResponse
import com.nullpointer.dogedex.models.auth.response.RegisterResponse
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
    ): RegisterResponse

    @POST(SIGN_IN_PATH)
    suspend fun signIn(
        @Body data: SignInDTO
    ): LoginResponse
}