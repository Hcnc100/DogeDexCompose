package com.d34th.nullpointer.dogedex.models.dtos

data class SignUpDTO(
    val email: String,
    val password: String,
    val password_confirmation: String
)