package com.d34th.nullpointer.dogedex.models

data class Dog(
    val id: Long,
    val index: Long,
    val name: String,
    val type: String,
    val heightFemale: Double,
    val heightMale: Double,
    val imgUrl: String,
    val lifeExpectancy: String,
    val temperament:String,
    val weightFemale: String,
    val weightMale: String,
)