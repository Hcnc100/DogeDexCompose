package com.d34th.nullpointer.dogedex.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "dogs")
@Parcelize
data class Dog(
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
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
) : Parcelable, Comparable<Dog> {
    override fun compareTo(other: Dog): Int = if (this.index > other.index) -1 else 1
}