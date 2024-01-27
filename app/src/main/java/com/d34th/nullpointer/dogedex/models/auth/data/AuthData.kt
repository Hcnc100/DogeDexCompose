package com.d34th.nullpointer.dogedex.models.auth.data

import com.d34th.nullpointer.dogedex.models.authDogApiResponse.auth.AuthResponse
import kotlinx.serialization.Serializable


@Serializable
data class AuthData(
    val id: Int =-1,
    val token: String = "",
    val username: String = "",
    val email: String = "",
) {
    companion object {
        fun fromAuthResponse(response: AuthResponse): AuthData {
            return if (response.isSuccess) {
                val data = response.data
                AuthData(
                    id = data.user.id,
                    token = data.user.authentication_token,
                    // ! TODO: change this
                    username ="",
                    email = data.user.email
                )
            } else {
                throw Exception(response.message)
            }
        }
    }
}
