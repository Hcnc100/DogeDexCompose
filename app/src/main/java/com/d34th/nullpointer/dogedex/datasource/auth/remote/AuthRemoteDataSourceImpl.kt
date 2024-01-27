package com.d34th.nullpointer.dogedex.datasource.auth.remote

import com.d34th.nullpointer.dogedex.data.auth.remote.AuthApiServices
import com.d34th.nullpointer.dogedex.data.dogs.remote.DogsApiServices
import com.d34th.nullpointer.dogedex.data.dogs.remote.callApiDogsWithTimeOut
import com.d34th.nullpointer.dogedex.models.User
import com.d34th.nullpointer.dogedex.models.auth.data.AuthData
import com.d34th.nullpointer.dogedex.models.authDogApiResponse.auth.AuthResponse
import com.d34th.nullpointer.dogedex.models.authDogApiResponse.auth.UserResponse
import com.d34th.nullpointer.dogedex.models.auth.dto.SignInDTO
import com.d34th.nullpointer.dogedex.models.auth.dto.SignUpDTO

class AuthRemoteDataSourceImpl(
    private val authApiServices: AuthApiServices
) : AuthRemoteDataSource {
    override suspend fun signUp(userCredentials: SignUpDTO): AuthData {
        val response=callApiDogsWithTimeOut {
            authApiServices.signUp(userCredentials)
        }

        return AuthData.fromAuthResponse(response)
    }



    override suspend fun signIn(userCredentials: SignInDTO):AuthData {
        val response=callApiDogsWithTimeOut {
            authApiServices.signIn(userCredentials)
        }
        return AuthData.fromAuthResponse(response)
    }

}