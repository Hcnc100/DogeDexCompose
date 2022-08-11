package com.d34th.nullpointer.dogedex.models.listDogsApi

data class DogsApiResponse(
    val `data`: Data,
    val is_success: Boolean,
    val message: String
)