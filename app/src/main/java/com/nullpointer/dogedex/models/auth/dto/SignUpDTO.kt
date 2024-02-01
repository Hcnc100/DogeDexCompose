package com.nullpointer.dogedex.models.auth.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class SignUpDTO(
    @SerialName("email")
    val email: String,
    @SerialName("password")
    val password: String,
    @SerialName("password_confirmation")
    val passwordConfirmation: String
)