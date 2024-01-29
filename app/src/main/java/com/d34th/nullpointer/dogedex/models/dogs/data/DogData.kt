package com.d34th.nullpointer.dogedex.models.dogs.data

import com.d34th.nullpointer.dogedex.models.dogs.entity.DogEntity
import com.d34th.nullpointer.dogedex.models.dogs.response.ListDogsResponse
import com.d34th.nullpointer.dogedex.models.dogs.response.ListMyDogsResponse
import kotlinx.serialization.Serializable


@Serializable
data class DogData(
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
        fun fromDogResponse(dogResponse: ListDogsResponse.Data.Dog): DogData {
            return DogData(
                hasDog = false,
                id = dogResponse.id!!,
                name = dogResponse.nameEs!!,
                imgUrl = dogResponse.imageURL!!,
                type = dogResponse.dogType!!,
                temperament = dogResponse.temperament!!,
                weightMale = dogResponse.weightMale!!,
                weightFemale = dogResponse.weightFemale!!,
                heightMale = dogResponse.heightMale!!.toDouble(),
                lifeExpectancy = dogResponse.lifeExpectancy!!,
                heightFemale = dogResponse.heightFemale!!.toDouble(),
                model = dogResponse.mlID!!
            )
        }

        fun fromMyDogResponse(dogResponse: ListMyDogsResponse.Data.Dog): DogData {
            return DogData(
                hasDog = true,
                id = dogResponse.id!!,
                name = dogResponse.nameEs!!,
                imgUrl = dogResponse.imageURL!!,
                type = dogResponse.dogType!!,
                temperament = dogResponse.temperament!!,
                weightMale = dogResponse.weightMale!!,
                weightFemale = dogResponse.weightFemale!!,
                heightMale = dogResponse.heightMale!!.toDouble(),
                lifeExpectancy = dogResponse.lifeExpectancy!!,
                heightFemale = dogResponse.heightFemale!!.toDouble(),
                model = dogResponse.mlID!!
            )
        }


        fun fromDogEntity(dogEntity: DogEntity): DogData {
            return DogData(
                hasDog = dogEntity.hasDog,
                id = dogEntity.id,
                name = dogEntity.name,
                imgUrl = dogEntity.imgUrl,
                type = dogEntity.type,
                temperament = dogEntity.temperament,
                weightMale = dogEntity.weightMale,
                weightFemale = dogEntity.weightFemale,
                heightMale = dogEntity.heightMale,
                lifeExpectancy = dogEntity.lifeExpectancy,
                heightFemale = dogEntity.heightFemale,
                model = dogEntity.model
            )
        }

    }
}