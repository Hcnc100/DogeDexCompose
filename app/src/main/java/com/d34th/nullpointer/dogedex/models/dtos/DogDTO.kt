package com.d34th.nullpointer.dogedex.models.dtos

data class DogDTO(
    val created_at: String,
    val dog_type: String,
    val height_female: String,
    val height_male: String,
    val id: Long,
    val image_url: String,
    val index: Long,
    val life_expectancy: String,
    val ml_id: String,
    val name_en: String,
    val name_es: String,
    val temperament: String,
    val temperament_en: String,
    val updated_at: String,
    val weight_female: String,
    val weight_male: String
)