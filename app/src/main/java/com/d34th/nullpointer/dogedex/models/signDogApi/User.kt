package com.d34th.nullpointer.dogedex.models.signDogApi

data class User(
    val authentication_token: String,
    val created_at: String,
    val email: String,
    val id: Int,
    val updated_at: String
)