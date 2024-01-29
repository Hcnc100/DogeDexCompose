package com.d34th.nullpointer.dogedex.models.findDogByModel.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FindDogByModelResponse(
    val message: String? = null,

    @SerialName("is_success")
    val isSuccess: Boolean? = null,

    val data: Data? = null
) {
    @Serializable
    data class Data(
        val dog: Dog? = null
    ) {
        @Serializable
        data class Dog(
            val id: Long? = null,

            @SerialName("dog_type")
            val dogType: String? = null,

            @SerialName("height_female")
            val heightFemale: String? = null,

            @SerialName("height_male")
            val heightMale: String? = null,

            @SerialName("image_url")
            val imageURL: String? = null,

            val index: Long? = null,

            @SerialName("life_expectancy")
            val lifeExpectancy: String? = null,

            @SerialName("name_en")
            val nameEn: String? = null,

            @SerialName("name_es")
            val nameEs: String? = null,

            val temperament: String? = null,

            @SerialName("temperament_en")
            val temperamentEn: String? = null,

            @SerialName("weight_female")
            val weightFemale: String? = null,

            @SerialName("weight_male")
            val weightMale: String? = null,

            @SerialName("created_at")
            val createdAt: String? = null,

            @SerialName("updated_at")
            val updatedAt: String? = null,

            @SerialName("ml_id")
            val mlID: String? = null
        )

    }

}


