package com.d34th.nullpointer.dogedex.models.authDogApi

data class AuthApiResponse(
    val `data`: Data,
    val is_success: Boolean,
    val message: String
)