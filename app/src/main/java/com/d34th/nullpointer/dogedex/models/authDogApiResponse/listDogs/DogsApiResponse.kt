package com.d34th.nullpointer.dogedex.models.authDogApiResponse.listDogs

data class DogsApiResponse(
    val data: Data,
    val is_success: Boolean,
    val message: String
)