package com.d34th.nullpointer.dogedex.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "dogs")
@Parcelize
data class Dog(
    @PrimaryKey
    val index: Long = 0,
    val name: String = "",
    val type: String = "",
    val heightFemale: Double = 0.0,
    val heightMale: Double = 0.0,
    val imgUrl: String = "",
    val lifeExpectancy: String = "",
    val temperament: String = "",
    val weightFemale: String = "",
    val weightMale: String = "",
    val hasDog: Boolean = false,
) : Parcelable