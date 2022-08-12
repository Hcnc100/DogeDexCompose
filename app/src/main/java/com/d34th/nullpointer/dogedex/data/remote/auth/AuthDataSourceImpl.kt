package com.d34th.nullpointer.dogedex.data.remote.auth

import com.d34th.nullpointer.dogedex.data.remote.DogsApiServices
import com.d34th.nullpointer.dogedex.data.remote.callApiWithTimeout
import com.d34th.nullpointer.dogedex.models.ApiResponse
import com.d34th.nullpointer.dogedex.models.User
import com.d34th.nullpointer.dogedex.models.authDogApi.UserResponse
import com.d34th.nullpointer.dogedex.models.dtos.SignInDTO
import com.d34th.nullpointer.dogedex.models.dtos.SignUpDTO

class AuthDataSourceImpl(
    private val dogsApiServices: DogsApiServices
) : AuthDataSource {
    override suspend fun signUp(userCredentials: SignUpDTO): ApiResponse<User> {
        val user = callApiWithTimeout(timeout = 10_000) {
            val userResponse = dogsApiServices.signUp(userCredentials)
            if (!userResponse.is_success) throw Exception(userResponse.message)
            userResponse.data.user.toUser()
        }
        return user
    }

    override suspend fun signIn(userCredentials: SignInDTO): ApiResponse<User> {
        val user = callApiWithTimeout(timeout = 10_000) {
            val userResponse = dogsApiServices.signIn(userCredentials)
            if (!userResponse.is_success) throw Exception(userResponse.message)
            userResponse.data.user.toUser()
        }
        return user
    }
}

private fun UserResponse.toUser(): User {
    return User(
        id = id.toLong(),
        email = email,
        token = authentication_token
    )
}