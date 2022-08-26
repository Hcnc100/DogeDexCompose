package com.d34th.nullpointer.dogedex.models.authDogApiResponse.simpleDog

import com.google.gson.annotations.SerializedName

data class DogApiResponse(
    val data: Data,
    @SerializedName("is_success")
    val isSuccess: Boolean,
    val message: String
)