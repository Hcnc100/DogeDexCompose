package com.d34th.nullpointer.dogedex.models.auth.data

import com.d34th.nullpointer.dogedex.models.auth.response.LoginResponse
import com.d34th.nullpointer.dogedex.models.auth.response.RegisterResponse
import kotlinx.serialization.Serializable


@Serializable
data class AuthData(
    val id: Int = -1,
    val token: String = "",
    val email: String = "",
    val password: String = "",
) {
    companion object {
        fun fromRegisterResponse(response: RegisterResponse): AuthData {
            return if (response.isSuccess == true) {
                val data = response.data!!
                AuthData(
                    email = data.user!!.email!!,
                    id = data.user.id!!.toInt(),
                    token = data.user.authenticationToken!!
                )
            } else {
                throw Exception(response.message)
            }
        }


        fun fromLoginResponse(response: LoginResponse): AuthData {
            return if (response.isSuccess == true) {
                val data = response.data!!
                AuthData(
                    email = data.user!!.email!!,
                    id = data.user.id!!.toInt(),
                    token = data.user.authenticationToken!!
                )
            } else {
                throw Exception(response.message)
            }
        }
    }
}
