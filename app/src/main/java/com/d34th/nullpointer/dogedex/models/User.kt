package com.d34th.nullpointer.dogedex.models

import com.d34th.nullpointer.dogedex.models.authDogApiResponse.auth.AuthResponse

data class User(
    val id: Long = -1,
    val email: String = "",
    val token: String = ""
) {
    val isAuth: Boolean get() = id != -1L

    companion object {
        fun fromAuthResponse(response: AuthResponse): User {
            return if (response.isSuccess) {
                val data = response.data
                User(
                    id = data.user.id.toLong(),
                    token = data.user.authentication_token,
                    email = data.user.email
                )
            } else {
                throw Exception(response.message)
            }
        }
    }
}