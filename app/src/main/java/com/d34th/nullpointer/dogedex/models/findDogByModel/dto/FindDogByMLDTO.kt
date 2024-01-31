package com.d34th.nullpointer.dogedex.models.findDogByModel.dto

import com.d34th.nullpointer.dogedex.ia.DogRecognition

data class FindDogByModelDTO(
    val modelId: String,
) {
    companion object {
        fun fromDogRecognition(
            dogRecognition: DogRecognition
        ): FindDogByModelDTO {
            return FindDogByModelDTO(dogRecognition.id)
        }
    }
}
