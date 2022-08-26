package com.d34th.nullpointer.dogedex.models.authDogApiResponse.auth

import com.google.gson.annotations.SerializedName

data class AuthResponse(
    val data: Data,
    @SerializedName("is_success")
    val isSuccess: Boolean,
    val message: String
)