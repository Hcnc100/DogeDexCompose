package com.d34th.nullpointer.dogedex.models.authDogApiResponse.defaultDogs

import com.google.gson.annotations.SerializedName

data class DefaultResponse(
    val message: String,
    @SerializedName("is_success")
    val isSuccess: Boolean
)