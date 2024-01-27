package com.d34th.nullpointer.dogedex.models.auth.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class SignInDTO(
    @SerialName("email")
    val email: String,
    @SerialName("password")
    val password: String,
)