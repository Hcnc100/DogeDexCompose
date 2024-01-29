package com.d34th.nullpointer.dogedex.models.auth.dto

import com.d34th.nullpointer.dogedex.models.auth.data.AuthData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class SignInDTO(
    @SerialName("email")
    val email: String,
    @SerialName("password")
    val password: String,
) {
    companion object {
        fun fromAuthData(authData: AuthData): SignInDTO {
            return SignInDTO(
                email = authData.email,
                password = authData.password
            )
        }
    }
}