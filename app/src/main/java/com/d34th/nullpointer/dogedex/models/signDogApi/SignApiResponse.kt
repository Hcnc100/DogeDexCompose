package com.d34th.nullpointer.dogedex.models.signDogApi

data class SignApiResponse(
    val `data`: Data,
    val is_success: Boolean,
    val message: String
)