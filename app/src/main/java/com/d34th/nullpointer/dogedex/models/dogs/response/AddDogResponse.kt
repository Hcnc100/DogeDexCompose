package com.d34th.nullpointer.dogedex.models.dogs.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddDogResponse(
    val message: String? = null,

    @SerialName("is_success")
    val isSuccess: Boolean? = null,

    val data: Data? = null
) {
    @Serializable
    class Data()
}


