package com.d34th.nullpointer.dogedex.data.remote.auth

import com.d34th.nullpointer.dogedex.data.remote.DogsApiServices
import com.d34th.nullpointer.dogedex.data.remote.callApiDogsWithTimeOut
import com.d34th.nullpointer.dogedex.models.User
import com.d34th.nullpointer.dogedex.models.authDogApiResponse.auth.AuthResponse
import com.d34th.nullpointer.dogedex.models.authDogApiResponse.auth.UserResponse
import com.d34th.nullpointer.dogedex.models.dtos.SignInDTO
import com.d34th.nullpointer.dogedex.models.dtos.SignUpDTO

class AuthDataSourceImpl(
    private val dogsApiServices: DogsApiServices
) : AuthDataSource {
    override suspend fun signUp(userCredentials: SignUpDTO) =
        callApiDogsWithTimeOut {
            dogsApiServices.signUp(userCredentials)
        }.toUser()


    override suspend fun signIn(userCredentials: SignInDTO) =
        callApiDogsWithTimeOut(timeout = 10_000) {
            dogsApiServices.signIn(userCredentials)
        }.toUser()


    private fun AuthResponse.toUser(): User {
        return if (isSuccess) data.user.toUser() else throw Exception(message)
    }

    private fun UserResponse.toUser(): User {
        return User(
            id = id.toLong(),
            email = email,
            token = authentication_token
        )
    }
}