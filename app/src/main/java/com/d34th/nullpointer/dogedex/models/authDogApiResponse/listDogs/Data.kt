package com.d34th.nullpointer.dogedex.models.authDogApiResponse.listDogs

import com.d34th.nullpointer.dogedex.models.dtos.DogDTO

data class Data(
    val dogs: List<DogDTO>
)