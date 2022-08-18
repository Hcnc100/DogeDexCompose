package com.d34th.nullpointer.dogedex.models.authDogApiResponse.simpleDog

data class DogApiResponse(
    val data: Data,
    val is_success: Boolean,
    val message: String
)