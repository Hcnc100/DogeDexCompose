package com.d34th.nullpointer.dogedex.models.auth.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class RegisterResponse(
    val message: String? = null,

    @SerialName("is_success")
    val isSuccess: Boolean? = null,

    val data: Data? = null
) {
    @Serializable
    data class Data(
        val user: User? = null
    ) {
        @Serializable
        data class User(
            val id: Long? = null,
            val email: String? = null,

            @SerialName("created_at")
            val createdAt: String? = null,

            @SerialName("updated_at")
            val updatedAt: String? = null,

            @SerialName("authentication_token")
            val authenticationToken: String? = null
        )

    }

}


