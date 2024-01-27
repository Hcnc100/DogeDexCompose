package com.d34th.nullpointer.dogedex.models.authDogApiResponse.auth

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val data: Data,
    @SerialName("is_success")
    val isSuccess: Boolean,
    val message: String
)