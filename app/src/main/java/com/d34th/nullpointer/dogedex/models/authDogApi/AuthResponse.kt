package com.d34th.nullpointer.dogedex.models.authDogApi

data class AuthResponse(
    val `data`: Data,
    val is_success: Boolean,
    val message: String
)