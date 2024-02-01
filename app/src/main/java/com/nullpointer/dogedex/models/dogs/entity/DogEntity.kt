package com.nullpointer.dogedex.models.dogs.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nullpointer.dogedex.models.dogs.data.DogData

@Entity(tableName = "dogs")
data class DogEntity(
    @PrimaryKey
    val id: Long,
    val name: String,
    val type: String,
    val heightFemale: Double,
    val heightMale: Double,
    val imgUrl: String,
    val lifeExpectancy: String,
    val temperament: String,
    val weightFemale: String,
    val weightMale: String,
    val hasDog: Boolean,
    val model: String
) {
    companion object {
        fun fromDogData(dogData: DogData): DogEntity {
            return DogEntity(
                hasDog = dogData.hasDog,
                id = dogData.id,
                name = dogData.name,
                imgUrl = dogData.imgUrl,
                type = dogData.type,
                temperament = dogData.temperament,
                weightMale = dogData.weightMale,
                weightFemale = dogData.weightFemale,
                heightMale = dogData.heightMale,
                lifeExpectancy = dogData.lifeExpectancy,
                heightFemale = dogData.heightFemale,
                model = dogData.model
            )
        }
    }
}