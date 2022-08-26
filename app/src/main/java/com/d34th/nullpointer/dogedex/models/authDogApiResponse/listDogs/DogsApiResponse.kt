package com.d34th.nullpointer.dogedex.models.authDogApiResponse.listDogs

import com.google.gson.annotations.SerializedName

data class DogsApiResponse(
    val data: Data,
    @SerializedName("is_success")
    val isSuccess: Boolean,
    val message: String
)