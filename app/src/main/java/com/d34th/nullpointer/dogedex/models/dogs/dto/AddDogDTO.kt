package com.d34th.nullpointer.dogedex.models.dogs.dto

import com.d34th.nullpointer.dogedex.models.dogs.data.DogData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddDogDTO(
    @SerialName("dog_id")
    val id: Long
) {
    companion object {
        fun fromDogData(dogData: DogData): AddDogDTO {
            return AddDogDTO(
                id = dogData.id
            )
        }
    }
}