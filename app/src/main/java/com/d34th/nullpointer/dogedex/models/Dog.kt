package com.d34th.nullpointer.dogedex.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Dog(
    val id: Long,
    val index: Long,
    val name: String,
    val type: String,
    val heightFemale: Double,
    val heightMale: Double,
    val imgUrl: String,
    val lifeExpectancy: String,
    val temperament: String,
    val weightFemale: String,
    val weightMale: String,
) : Parcelable, Comparable<Dog> {
    override fun compareTo(other: Dog): Int = if (this.index > other.index) -1 else 1
}