package com.d34th.nullpointer.dogedex.models.findDogByModel.dto

import com.d34th.nullpointer.dogedex.models.dogs.data.DogData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FindDogByModelDTO(
    @SerialName("ml_id")
    val idModel: String,
) {
    companion object {
        fun fromDogData(dogData: DogData): FindDogByModelDTO {
            return FindDogByModelDTO(
                idModel = dogData.id.toString()
            )
        }
    }


}
